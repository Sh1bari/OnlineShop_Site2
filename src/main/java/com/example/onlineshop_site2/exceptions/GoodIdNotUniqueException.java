package com.example.onlineshop_site2.exceptions;

import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class GoodIdNotUniqueException extends GlobalAppException{
    public GoodIdNotUniqueException(String message) {
        super(409, message);
    }
}
