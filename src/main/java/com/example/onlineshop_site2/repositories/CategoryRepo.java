package com.example.onlineshop_site2.repositories;

import com.example.onlineshop_site2.models.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepo extends CrudRepository<Category, Long> {
}
