package com.example.onlineshop_site2.exceptions;

import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class ApplicationNotFoundException extends GlobalAppException{
    public ApplicationNotFoundException(Long id) {
        super(404, "Application not found with id " + id);
    }
    public ApplicationNotFoundException(String email) {
        super(404, "Application not found by email " + email);
    }
}
