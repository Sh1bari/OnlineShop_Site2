package com.example.onlineshop_site2.repositories;

import com.example.onlineshop_site2.models.entities.Size;
import lombok.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public interface SizeRepo extends CrudRepository<Size, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM sizes WHERE id = ?1", nativeQuery = true)
    void deleteByIdNative(Long id);
}
