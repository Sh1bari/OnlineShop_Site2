package com.example.onlineshop_site2.repositories;

import com.example.onlineshop_site2.models.entities.UserBag;
import lombok.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public interface UserBagRepo extends CrudRepository<UserBag, Long> {
    @Modifying
    @Query(value = "DELETE FROM user_bag WHERE size_id = ?1", nativeQuery = true)
    void deleteBySizeId(Long sizeId);
}
