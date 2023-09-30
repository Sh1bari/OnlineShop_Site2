package com.example.onlineshop_site2.models.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SectionDto {
    private String name;
    private List<CategoryDto> categories;
}
