package com.javamoon.spring_algaworks.domain.exception;

public class CityNotFoundException extends EntityNotFoundExeption{

    public CityNotFoundException() {
        super();
    }

    public CityNotFoundException(Long id) {
        super("City not found with id: " + id);
    }
}
