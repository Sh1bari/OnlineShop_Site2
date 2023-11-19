package com.example.onlineshop_site2.services;

import com.example.onlineshop_site2.models.dtos.requests.GoodCreateReq;
import com.example.onlineshop_site2.models.dtos.responses.GoodResDto;
import com.example.onlineshop_site2.models.entities.Color;
import com.example.onlineshop_site2.models.entities.Good;
import com.example.onlineshop_site2.repositories.GoodRepo;
import com.example.onlineshop_site2.repositories.SizeRepo;
import com.example.onlineshop_site2.services.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GoodServiceImpl implements GoodService {

    private final GoodRepo goodRepo;
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
}
