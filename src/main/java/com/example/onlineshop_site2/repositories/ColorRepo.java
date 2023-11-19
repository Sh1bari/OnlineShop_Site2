package com.example.onlineshop_site2.repositories;

import com.example.onlineshop_site2.models.entities.Color;
import lombok.*;
import org.springframework.data.repository.CrudRepository;


public interface ColorRepo extends CrudRepository<Color, Long> {
}
