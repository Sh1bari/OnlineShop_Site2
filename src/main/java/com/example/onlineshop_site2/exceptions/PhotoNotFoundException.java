package com.example.onlineshop_site2.exceptions;

import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class PhotoNotFoundException extends GlobalAppException{
    public PhotoNotFoundException(Long id) {
        super(404, "Photo not found with id " + id);
    }
}
