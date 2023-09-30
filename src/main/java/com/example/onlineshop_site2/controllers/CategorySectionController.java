package com.example.onlineshop_site2.controllers;

import com.example.onlineshop_site2.services.service.CategorySectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class CategorySectionController {

    private final CategorySectionService categorySectionService;

    @GetMapping("/sections")
    private ResponseEntity<?> getSections(){
        return ResponseEntity.ok(categorySectionService.getSections());
    }

}