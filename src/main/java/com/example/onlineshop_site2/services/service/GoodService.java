package com.example.onlineshop_site2.services.service;

import com.example.onlineshop_site2.models.dtos.GoodDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GoodService {

    Page<GoodDto> getGoodsByCategoryIdWithPage(Long categoryId, Integer page);
    Integer getAmountOfGoodPages();
}
