package com.example.onlineshop_site2.repositories;

import com.example.onlineshop_site2.models.entities.Application;
import com.example.onlineshop_site2.models.enums.ApplicationStatus;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public interface ApplicationRepo extends CrudRepository<Application, Long> {

    List<Application> findAllByUser_id(Long id);
    Page<Application> findAllByStatus(ApplicationStatus status, Pageable pageable);
    Page<Application> findAll(Pageable pageable);
    Page<Application> findAllByUser_id(Long id, Pageable pageable);
}
