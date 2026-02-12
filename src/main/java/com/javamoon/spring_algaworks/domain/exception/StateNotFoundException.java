package com.javamoon.spring_algaworks.domain.exception;

public class StateNotFoundException extends EntityNotFoundExeption{

    public StateNotFoundException() {
        super();
    }

    public StateNotFoundException(Long id) {
        super("State not found with id: " + id);
    }
}
