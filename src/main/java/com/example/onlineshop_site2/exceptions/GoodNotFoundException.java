package com.example.onlineshop_site2.exceptions;

import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class GoodNotFoundException extends GlobalAppException{
    public GoodNotFoundException(Long id) {
        super(404, "Good not found with id " + id);
    }
}
