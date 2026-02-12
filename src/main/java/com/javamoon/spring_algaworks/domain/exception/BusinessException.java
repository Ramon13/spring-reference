package com.javamoon.spring_algaworks.domain.exception;

public class BusinessException extends RuntimeException{

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
