package com.javamoon.spring_algaworks.infrastructure.repository;

import java.util.Objects;

import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.javamoon.spring_algaworks.domain.model.Cuisine;
import com.javamoon.spring_algaworks.domain.repository.CuisineRepositoryCustom;

import jakarta.persistence.EntityManager;

@Repository
public class CuisineRepositoryImpl implements CuisineRepositoryCustom{

    @Autowired
    private EntityManager manager;

    @Override
    public void remove(Cuisine cuisine) {
        try {
            if (Objects.nonNull(cuisine)) {
                manager.remove(cuisine);
                manager.flush();
            }
        } catch (GenericJDBCException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }
    }

}
