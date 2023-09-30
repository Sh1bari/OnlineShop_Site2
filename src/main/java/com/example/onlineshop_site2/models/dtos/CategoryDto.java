package com.example.onlineshop_site2.models.dtos;

import com.example.onlineshop_site2.models.entities.Category;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CategoryDto {
    private Long id;
    private String name;

    public static List<CategoryDto> mapToCategoryDtoList(List<Category> categoryList){
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        categoryList.forEach(category -> categoryDtoList.add(CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build()));
        return categoryDtoList;
    }
}
