package com.example.onlineshop_site2.services;

import com.example.onlineshop_site2.models.dtos.requests.GoodAddColorReq;
import com.example.onlineshop_site2.models.dtos.requests.GoodAddSizeReq;
import com.example.onlineshop_site2.models.dtos.requests.GoodUpdateReq;
import com.example.onlineshop_site2.models.dtos.requests.CategoryIdReq;
import com.example.onlineshop_site2.exceptions.CategoryNotFoundException;
import com.example.onlineshop_site2.exceptions.GoodNotFoundException;
import com.example.onlineshop_site2.models.dtos.requests.GoodCreateReq;
import com.example.onlineshop_site2.models.dtos.responses.CategoryIdRes;
import com.example.onlineshop_site2.models.dtos.responses.GoodResDto;
import com.example.onlineshop_site2.models.entities.*;
import com.example.onlineshop_site2.models.enums.ColorType;
import com.example.onlineshop_site2.repositories.CategoryRepo;
import com.example.onlineshop_site2.repositories.ColorRepo;
import com.example.onlineshop_site2.repositories.GoodRepo;
import com.example.onlineshop_site2.repositories.SizeRepo;
import com.example.onlineshop_site2.services.service.GoodService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
    private final ColorRepo colorRepo;


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

    @Override
    public GoodResDto createGood(GoodCreateReq req) {
        Good res = goodRepo.save(req.mapToEntity());
        res.getColors().forEach(o->o.getSizes().forEach(s->{
            s.setColor(o);
            s.setGood(res);
            sizeRepo.save(s);
        }));
        return GoodResDto.mapFromEntity(res);
    }

    @Transactional
    @Override
    public GoodResDto updateGood(Long id, GoodUpdateReq req) {
        // Найти товар по id
        Good existingGood = goodRepo.findById(id)
                .orElseThrow(() -> new GoodNotFoundException(id));
        Good newGood = req.mapToEntity();
        existingGood.setName(newGood.getName());
        existingGood.setDescription(newGood.getDescription());
        existingGood.setCompound(newGood.getCompound());
        existingGood.getColors().forEach(o->{
            if(o.getColorType().equals(ColorType.BACKGROUND)){
                colorRepo.deleteByIdNative(o.getId());
            }
        });
        Color color = newGood.getBackColor();
        color.setGood(existingGood);
        color.setGoodBackColor(existingGood);
        colorRepo.save(color);
        existingGood.setBackColor(color);
        goodRepo.save(existingGood);

        return GoodResDto.mapFromEntity(existingGood);
    }

    @Transactional
    public GoodResDto putSizes(Long id, GoodAddSizeReq req){
        Good existingGood = goodRepo.findById(id)
                .orElseThrow(() -> new GoodNotFoundException(id));
        Good newGood = req.mapToEntity();
        sizeRepo.saveAll(newGood.getSizes());
        existingGood.getSizes().clear();
        existingGood.getSizes().addAll(newGood.getSizes());
        existingGood.getSizes().forEach(o->o.setGood(existingGood));
        goodRepo.save(existingGood);
        return GoodResDto.mapFromEntity(existingGood);
    }
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public GoodResDto putColors(Long id, GoodAddColorReq req) throws InterruptedException {
        Good existingGood = goodRepo.findById(id)
                .orElseThrow(() -> new GoodNotFoundException(id));
        Good newGood = req.mapToEntity();

        existingGood.getColors().forEach(o->{
            if(o.getColorType().equals(ColorType.WITH_SIZE)) {
                o.getSizes().forEach(s->{
                    sizeRepo.deleteByIdNative(s.getId());
                });
                colorRepo.deleteByIdNative(o.getId());
            }
        });
        entityManager.clear();

        Good res = goodRepo.findById(id)
                .orElseThrow(() -> new GoodNotFoundException(id));

        newGood.getColors().forEach(o->{
            o.setGood(res);
            Color saved = colorRepo.save(o);
            o.getSizes().forEach(s->{
                Size size = s;
                size.setColor(saved);
                size.setGood(res);
                sizeRepo.save(size);
            });
        });


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
