package com.example.onlineshop_site2.repositories;

import com.example.onlineshop_site2.models.entities.Application;
import lombok.*;
import org.springframework.data.repository.CrudRepository;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public interface ApplicationRepo extends CrudRepository<Application, Long> {
}
