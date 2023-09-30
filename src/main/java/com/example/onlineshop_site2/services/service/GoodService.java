package com.example.onlineshop_site2.services.service;

import com.example.onlineshop_site2.models.dtos.GoodDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GoodService {

    List<GoodDto> getGoodsByCategoryIdWithPage(Long categoryId, Integer page);
    Integer getAmountOfGoodPages();
}
