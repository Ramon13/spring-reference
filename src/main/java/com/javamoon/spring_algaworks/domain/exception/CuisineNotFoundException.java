package com.javamoon.spring_algaworks.domain.exception;

public class CuisineNotFoundException extends EntityNotFoundExeption{

    public CuisineNotFoundException() {
        super();
    }

    public CuisineNotFoundException(Long id) {
        super("Cuisine not found with id: " + id);
    }
}
