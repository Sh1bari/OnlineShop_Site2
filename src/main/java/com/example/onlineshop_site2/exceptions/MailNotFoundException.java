package com.example.onlineshop_site2.exceptions;

import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class MailNotFoundException extends GlobalAppException{
    public MailNotFoundException(String email) {
        super(404, "Mail " + email + " not found");
    }
}
