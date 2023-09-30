package com.example.onlineshop_site2.services;

import com.example.onlineshop_site2.models.dtos.CategoryDto;
import com.example.onlineshop_site2.models.dtos.SectionDto;
import com.example.onlineshop_site2.models.entities.Section;
import com.example.onlineshop_site2.repositories.SectionRepo;
import com.example.onlineshop_site2.services.service.CategorySectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategorySectionServiceImpl implements CategorySectionService {

    private final SectionRepo sectionRepo;

    @Override
    public List<SectionDto> getSections() {
        Iterable<Section> sectionList = sectionRepo.findAll();
        List<SectionDto> response = new ArrayList<>();
        sectionList.forEach(section -> {
            response.add(SectionDto.builder()
                    .name(section.getName())
                    .categories(CategoryDto.mapToCategoryDtoList(section.getCategories()))
                    .build());
        });
        return response;
    }
}
