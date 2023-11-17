package com.example.onlineshop_site2.services.service;

import com.example.onlineshop_site2.exceptions.CategoryNotFoundException;
import com.example.onlineshop_site2.exceptions.SectionNotFoundException;
import com.example.onlineshop_site2.models.dtos.CategoryDto;
import com.example.onlineshop_site2.models.dtos.requests.CategoryCreateReq;
import com.example.onlineshop_site2.models.entities.Category;
import com.example.onlineshop_site2.repositories.CategoryRepo;
import com.example.onlineshop_site2.repositories.SectionRepo;
import lombok.*;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepo categoryRepo;
    private final SectionRepo sectionRepo;
    public CategoryDto getCategoryById(Long id){
        Category category = categoryRepo.findById(id).orElseThrow(()-> new CategoryNotFoundException(id));
        return CategoryDto.mapFromEntity(category);
    }
    public CategoryDto addCategory(CategoryCreateReq req){
        Category category = new Category();
        category.setName(req.getName());
        category.setSection(sectionRepo.findById(req.getSectionId())
                .orElseThrow(()-> new SectionNotFoundException(req.getSectionId())));
        categoryRepo.save(category);
        return CategoryDto.mapFromEntity(category);
    }
}
