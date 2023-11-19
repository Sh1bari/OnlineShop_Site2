package com.example.onlineshop_site2.services;

import com.example.onlineshop_site2.controllers.CategoryIdReq;
import com.example.onlineshop_site2.exceptions.CategoryNotFoundException;
import com.example.onlineshop_site2.exceptions.GoodNotFoundException;
import com.example.onlineshop_site2.models.dtos.requests.GoodCreateReq;
import com.example.onlineshop_site2.models.dtos.responses.CategoryIdRes;
import com.example.onlineshop_site2.models.dtos.responses.GoodResDto;
import com.example.onlineshop_site2.models.entities.Category;
import com.example.onlineshop_site2.models.entities.Color;
import com.example.onlineshop_site2.models.entities.Good;
import com.example.onlineshop_site2.repositories.CategoryRepo;
import com.example.onlineshop_site2.repositories.GoodRepo;
import com.example.onlineshop_site2.repositories.SizeRepo;
import com.example.onlineshop_site2.services.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoodServiceImpl implements GoodService {

    private final GoodRepo goodRepo;
    private final CategoryRepo categoryRepo;
    private final SizeRepo sizeRepo;
    private final Integer limit = 30;


    @Override
    public Page<GoodResDto> getGoodsByCategoryIdWithPage(Long categoryId, Integer page) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Good> goodPage = goodRepo.findAllByCategories_id(categoryId, pageable);
        return goodPage.map(GoodResDto::mapFromEntity);
    }

    @Override
    public Integer getAmountOfGoodPages() {
        return (int) Math.ceil((double) goodRepo.count() / limit);
    }

    @Transactional
    @Override
    public GoodResDto createGood(GoodCreateReq req) {
        Good res = goodRepo.save(req.mapToEntity());
        res.getColors().forEach(o->o.getSizes().forEach(s->{
            s.setColor(o);
            s.setGood(res);
            sizeRepo.save(s);
        }));
        res.getBackColor().setGood(res);
        return GoodResDto.mapFromEntity(res);
    }

    @Override
    public List<CategoryIdRes> connectCategoryToGood(Long id, List<CategoryIdReq> req) {
        Good good = goodRepo.findById(id)
                .orElseThrow(()->new GoodNotFoundException(id));
        good.setCategories(new ArrayList<>());
        List<Category> categories = req.stream()
                .map(o->{
                    Category cat = categoryRepo.findById(o.getId())
                        .orElseThrow(()->new CategoryNotFoundException(o.getId()));
                    good.getCategories().add(cat);
                    return cat;
                })
                .collect(Collectors.toList());
        goodRepo.save(good);

        return categories.stream()
                .map(CategoryIdRes::mapFromEntity)
                .collect(Collectors.toList());
    }
}
