package com.example.onlineshop_site2.services.service;

import com.example.onlineshop_site2.controllers.GoodUpdateReq;
import com.example.onlineshop_site2.models.dtos.requests.CategoryIdReq;
import com.example.onlineshop_site2.models.dtos.requests.GoodCreateReq;
import com.example.onlineshop_site2.models.dtos.responses.CategoryIdRes;
import com.example.onlineshop_site2.models.dtos.responses.GoodResDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GoodService {

    Page<GoodResDto> getGoodsByCategoryIdWithPage(Long categoryId, Integer page);
    Integer getAmountOfGoodPages();
    GoodResDto createGood(GoodCreateReq req);
    GoodResDto updateGood(Long id, GoodUpdateReq req);
    List<CategoryIdRes> connectCategoryToGood(Long id, List<CategoryIdReq> req);
}
