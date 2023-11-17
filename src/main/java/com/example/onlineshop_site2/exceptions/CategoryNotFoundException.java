package com.example.onlineshop_site2.exceptions;

import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class CategoryNotFoundException extends GlobalAppException{
    public CategoryNotFoundException(Long id) {
        super(404, "Category not found with id " + id);
    }
}
