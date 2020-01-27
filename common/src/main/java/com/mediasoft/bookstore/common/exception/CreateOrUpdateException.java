package com.mediasoft.bookstore.common.exception;

import javax.ejb.ApplicationException;

public class CreateOrUpdateException extends RuntimeException {

    public CreateOrUpdateException(String message) {
        super(message);
    }
}