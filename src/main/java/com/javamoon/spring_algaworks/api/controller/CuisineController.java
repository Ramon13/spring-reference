package com.javamoon.spring_algaworks.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.javamoon.spring_algaworks.domain.model.Cuisine;
import com.javamoon.spring_algaworks.domain.repository.CuisineRepository;
import com.javamoon.spring_algaworks.domain.service.CuisineCreationService;


@RestController
@RequestMapping("/cuisines")
@CrossOrigin(origins = "*")
public class CuisineController {

    @Autowired
    private CuisineRepository cuisineRepository;

    @Autowired
    private CuisineCreationService cuisineCreationService;

    @GetMapping
    public List<Cuisine> listAll() {
        return cuisineRepository.findAll();
    }

    @GetMapping("/{cuisineId}")
    public Cuisine find(@PathVariable Long cuisineId) {
        return cuisineCreationService.findOrElseThrow(cuisineId);
    }

    @SuppressWarnings("null")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cuisine create(@RequestBody Cuisine cuisine) {
        return cuisineRepository.save(cuisine);
    }

    @SuppressWarnings("null")
    @PutMapping("/{cuisineId}")
    public Cuisine update(@PathVariable Long cuisineId, @RequestBody Cuisine cuisine) {
        Cuisine currentCuisine = cuisineCreationService.findOrElseThrow(cuisineId);

        BeanUtils.copyProperties(cuisine, currentCuisine, "id");
        Cuisine persistedCuisine = cuisineRepository.save(currentCuisine);
    
        return persistedCuisine;
    }

    @DeleteMapping("/{cuisineId}")
    public ResponseEntity<Cuisine> remove(@PathVariable Long cuisineId) {
        cuisineCreationService.remove(cuisineId);
        return ResponseEntity.noContent().build();
    }
}
