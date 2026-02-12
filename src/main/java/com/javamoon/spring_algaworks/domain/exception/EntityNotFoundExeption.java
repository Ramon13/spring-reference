package com.javamoon.spring_algaworks.domain.exception;

public abstract class EntityNotFoundExeption extends RuntimeException {

    public EntityNotFoundExeption() {
        super();
    }

    public EntityNotFoundExeption(String mensagem) {
        super(mensagem);
    }
}
