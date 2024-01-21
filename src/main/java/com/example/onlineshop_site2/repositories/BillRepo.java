package com.example.onlineshop_site2.repositories;

import com.example.onlineshop_site2.models.entities.Bill;
import lombok.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public interface BillRepo extends CrudRepository<Bill, Long> {

    List<Bill> findAllByStatus(String status);
}
