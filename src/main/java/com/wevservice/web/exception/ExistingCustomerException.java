package com.wevservice.web.exception;

public class ExistingCustomerException extends RuntimeException{

    public ExistingCustomerException(String message) {
        super(message);
    }
}
