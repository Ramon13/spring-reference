package com.javamoon.spring_algaworks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.javamoon.spring_algaworks.domain.exception.CuisineNotFoundException;
import com.javamoon.spring_algaworks.domain.exception.EntityInUseException;
import com.javamoon.spring_algaworks.domain.model.Cuisine;
import com.javamoon.spring_algaworks.domain.repository.CuisineRepository;

import jakarta.transaction.Transactional;

@Service
public class CuisineCreationService {

    private static final String MSG_CUIZINE_IN_USE 
        = "Cuisine with id: %s it's in use and cannot be removed";

    @Autowired
    private CuisineRepository cuisineRepository;

    @SuppressWarnings("null")
    @Transactional
    public void remove(Long id) {    
        try {
            Cuisine cuisine = findOrElseThrow(id);
            cuisineRepository.remove(cuisine);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_CUIZINE_IN_USE, id), e);
        }
    }

    @SuppressWarnings("null")
    public Cuisine findOrElseThrow(Long cuisineId) {
        return cuisineRepository.findById(cuisineId)
            .orElseThrow(() -> new CuisineNotFoundException(cuisineId));
    }
}
