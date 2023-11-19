package com.example.onlineshop_site2.repositories;

import com.example.onlineshop_site2.models.entities.UserBag;
import lombok.*;
import org.springframework.data.repository.CrudRepository;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public interface UserBagRepo extends CrudRepository<UserBag, Long> {
}
