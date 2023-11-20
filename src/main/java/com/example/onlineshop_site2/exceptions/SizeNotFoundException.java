package com.example.onlineshop_site2.exceptions;

import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class SizeNotFoundException extends GlobalAppException{
    public SizeNotFoundException(Long id) {
        super(404, "Size not found with id " + id);
    }
}