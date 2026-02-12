package com.javamoon.spring_algaworks.domain.exception;

public class RestaurantNotFoundException extends EntityNotFoundExeption{

    public RestaurantNotFoundException() {
        super();    
    }

    public RestaurantNotFoundException(Long id) {
        super("Restaurant not found with id: " + id);
    }
}
