package com.example.onlineshop_site2.repositories;

import com.example.onlineshop_site2.models.entities.FiveCategory;
import lombok.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public interface FiveCategoryRepo extends CrudRepository<FiveCategory, Long> {
}
