package com.example.onlineshop_site2.models.dtos.responses;

import com.example.onlineshop_site2.models.dtos.CategoryDto;
import com.example.onlineshop_site2.models.entities.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectionDtoRes {
    private Long id;
    private String name;
    private List<CategoryDto> categories;

    public static SectionDtoRes mapFromEntity(Section section){
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        try{
            categoryDtoList.addAll(CategoryDto.mapToCategoryDtoList(section.getCategories()));
        }catch (Exception e){

        }
        return SectionDtoRes.builder()
                .id(section.getId())
                .name(section.getName())
                .categories(categoryDtoList)
                .build();
    }
}
