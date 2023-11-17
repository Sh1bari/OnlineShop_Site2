package com.example.onlineshop_site2.repositories;

import com.example.onlineshop_site2.models.entities.Good;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GoodRepo extends PagingAndSortingRepository<Good, Long> {
    Page<Good> findAllByCategories_id(Long categoryId, Pageable pageable);
    long count();

}
