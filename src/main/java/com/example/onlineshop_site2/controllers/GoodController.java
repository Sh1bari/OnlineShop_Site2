package com.example.onlineshop_site2.controllers;

import com.example.onlineshop_site2.exceptions.AppError;
import com.example.onlineshop_site2.services.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GoodController {

    private final GoodService goodService;

    @GetMapping("/goods/pages-amount")
    private ResponseEntity<?> getAmountOfGoodPages(){
        return ResponseEntity.ok(goodService.getAmountOfGoodPages());
    }

    @GetMapping("/goods")
    private ResponseEntity<?> getGoods(@RequestParam("categoryId") Long categoryId,
                                       @RequestParam("page") Integer page){
        if(page>0) {
            return ResponseEntity.ok(goodService.getGoodsByCategoryIdWithPage(categoryId, page));
        }else return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Amount of pages cant be less than 1"), HttpStatus.BAD_REQUEST);
    }
}
