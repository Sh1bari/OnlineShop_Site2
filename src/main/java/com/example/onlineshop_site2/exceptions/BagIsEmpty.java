package com.example.onlineshop_site2.exceptions;

import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class BagIsEmpty extends GlobalAppException{
    public BagIsEmpty() {
        super(404, "Empty bag");
    }
}
