package com.example.onlineshop_site2.exceptions;

import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class UserNotFoundException extends GlobalAppException{
    public UserNotFoundException(String message) {
        super(404, message);
    }
}
