package com.example.onlineshop_site2.services.service;

import com.example.onlineshop_site2.exceptions.CategoryNotFoundException;
import com.example.onlineshop_site2.models.dtos.CategoryDto;
import com.example.onlineshop_site2.models.entities.Category;
import com.example.onlineshop_site2.repositories.CategoryRepo;
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
    public CategoryDto getCategoryById(Long id){
        Category category = categoryRepo.findById(id).orElseThrow(()-> new CategoryNotFoundException(id));
        return CategoryDto.mapFromEntity(category);
    }
}
