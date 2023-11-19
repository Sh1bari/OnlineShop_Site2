package com.example.onlineshop_site2.services.service;

import com.example.onlineshop_site2.models.dtos.requests.GoodCreateReq;
import com.example.onlineshop_site2.models.dtos.responses.GoodResDto;
import org.springframework.data.domain.Page;

public interface GoodService {

    Page<GoodResDto> getGoodsByCategoryIdWithPage(Long categoryId, Integer page);
    Integer getAmountOfGoodPages();
    GoodResDto createGood(GoodCreateReq req);
}
