package com.javamoon.spring_algaworks.domain.exception;

public class EntityInUseException extends RuntimeException{

    public EntityInUseException() {
        super();
    }

    public EntityInUseException(String message) {
        super(message);
    }

    public EntityInUseException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
