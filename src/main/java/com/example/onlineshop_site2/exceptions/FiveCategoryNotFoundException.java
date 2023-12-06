package com.example.onlineshop_site2.exceptions;

import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class FiveCategoryNotFoundException extends GlobalAppException{
    public FiveCategoryNotFoundException(Long id) {
        super(404, "FiveCategory with id " + id + " not found");
    }
}
