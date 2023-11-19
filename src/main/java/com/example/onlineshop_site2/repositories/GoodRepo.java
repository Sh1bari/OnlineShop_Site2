package com.example.onlineshop_site2.repositories;

import com.example.onlineshop_site2.models.entities.Good;
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
    Page<Good> findAllByCategories_id(Long categoryId, Pageable pageable);
    long count();
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM sizes WHERE good_id = :goodId", nativeQuery = true)
    void deleteSizesByGoodId(@Param("goodId") Long goodId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM colors WHERE good_id = :goodId", nativeQuery = true)
    void deleteColorsByGoodId(@Param("goodId") Long goodId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE goods SET id = :newId WHERE id = :oldId", nativeQuery = true)
    void updateGoodId(@Param("oldId") Long oldId, @Param("newId") Long newId);

}
