package com.example.onlineshop_site2.repositories;

import com.example.onlineshop_site2.models.entities.Good;
import com.example.onlineshop_site2.models.enums.RecordState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface GoodRepo extends CrudRepository<Good, Long> {
    Page<Good> findAllByCategories_idAndState(Long categoryId,RecordState state, Pageable pageable);
    Page<Good> findAllByState(RecordState state, Pageable pageable);
    long count();
    Page<Good> findByCategoriesIsEmpty(Pageable pageable);

}
