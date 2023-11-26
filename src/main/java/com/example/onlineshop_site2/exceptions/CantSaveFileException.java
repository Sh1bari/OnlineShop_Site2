package com.example.onlineshop_site2.exceptions;

import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public class CantSaveFileException extends GlobalAppException {
    public CantSaveFileException() {
        super(500, "Cant save file");
    }
}
