package com.mediasoft.bookstore.common.exception;

import javax.ejb.ApplicationException;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}