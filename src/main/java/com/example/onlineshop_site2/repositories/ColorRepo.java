package com.example.onlineshop_site2.repositories;

import com.example.onlineshop_site2.models.entities.Color;
import lombok.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


public interface ColorRepo extends CrudRepository<Color, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM colors WHERE id = ?1", nativeQuery = true)
    void deleteByIdNative(Long id);
}
