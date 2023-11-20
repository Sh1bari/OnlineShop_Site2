package com.example.onlineshop_site2.exceptions;

import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class UserBagNotFoundException extends GlobalAppException{
    public UserBagNotFoundException(Long id) {
        super(404, "Size not found with id " + id);
    }
}