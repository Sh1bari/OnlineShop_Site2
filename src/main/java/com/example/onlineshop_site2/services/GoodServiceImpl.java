package com.example.onlineshop_site2.services;

import com.example.onlineshop_site2.models.dtos.GoodDto;
import com.example.onlineshop_site2.models.entities.Good;
import com.example.onlineshop_site2.repositories.GoodRepo;
import com.example.onlineshop_site2.services.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class GoodServiceImpl implements GoodService {

    private final GoodRepo goodRepo;
    private final Integer limit = 30;


    @Override
    public Page<GoodDto> getGoodsByCategoryIdWithPage(Long categoryId, Integer page) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Good> goodPage = goodRepo.findAllByCategories_id(categoryId, pageable);
        return goodPage.map(GoodDto::mapToGoodDto);
    }

    @Override
    public Integer getAmountOfGoodPages() {
        return (int) Math.ceil((double) goodRepo.count() / limit);
    }
}
