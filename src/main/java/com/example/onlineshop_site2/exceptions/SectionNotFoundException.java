package com.example.onlineshop_site2.exceptions;

import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class SectionNotFoundException extends GlobalAppException{
    public SectionNotFoundException(Long id) {
        super(404, "Section not found with id " + id);
    }
}
