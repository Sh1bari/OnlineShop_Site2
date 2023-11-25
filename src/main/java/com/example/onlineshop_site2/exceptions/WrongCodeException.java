package com.example.onlineshop_site2.exceptions;

import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class WrongCodeException extends GlobalAppException{

    public WrongCodeException() {
        super(404, "Wrong code");
    }
}
