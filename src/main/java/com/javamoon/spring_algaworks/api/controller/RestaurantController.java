package com.javamoon.spring_algaworks.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javamoon.spring_algaworks.Groups;
import com.javamoon.spring_algaworks.domain.exception.BusinessException;
import com.javamoon.spring_algaworks.domain.exception.CityNotFoundException;
import com.javamoon.spring_algaworks.domain.exception.CuisineNotFoundException;
import com.javamoon.spring_algaworks.domain.model.Restaurant;
import com.javamoon.spring_algaworks.domain.repository.RestaurantRepository;
import com.javamoon.spring_algaworks.domain.service.RestaurantCreationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.exc.IgnoredPropertyException;
import tools.jackson.databind.json.JsonMapper;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRepository repository;

    @Autowired
    private RestaurantCreationService creationService;

    @GetMapping
    public List<Restaurant> listAll() {
        List<Restaurant> restaurants = repository.findAll();
        return restaurants;
    }

    @GetMapping("/{restaurantId}")
    public Restaurant find(@PathVariable Long restaurantId) {
        return creationService.findOrElseThrow(restaurantId);
    }

    @PostMapping
    public ResponseEntity<?> save(
        @RequestBody @Valid Restaurant restaurant) {
        try {
            Restaurant restaurantNew = creationService.create(restaurant);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurantNew);
        } catch (CuisineNotFoundException | CityNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<?> edit(@PathVariable Long restaurantId, @RequestBody Restaurant restaurant) {
        try {
            Restaurant currentRestaurant = creationService.findOrElseThrow(restaurantId);
    
            BeanUtils.copyProperties(restaurant, currentRestaurant, 
                "id", "paymentMethods", "address", "creationTimestamp");
            creationService.create(currentRestaurant);
            return ResponseEntity.ok(currentRestaurant);

        } catch (CuisineNotFoundException | CityNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PatchMapping("/{restaurantId}")
    public ResponseEntity<?> partiallyEdit(@PathVariable Long restaurantId, @RequestBody Map<String, Object> fields,
        HttpServletRequest request) {
        ServletServerHttpRequest servletHttpRequest = new ServletServerHttpRequest(request);

        try {
            Restaurant targetRestaurant = creationService.findOrElseThrow(restaurantId);
            ObjectMapper mapper = JsonMapper.builder()
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true)
                .build();
    
            Restaurant originRestaurant = mapper.convertValue(fields, Restaurant.class);
    
            fields.forEach((fieldName, fieldValue) -> {
                Field field = ReflectionUtils.findField(Restaurant.class, fieldName);    
                field.setAccessible(true);
    
                Object originValue = ReflectionUtils.getField(field, originRestaurant);
                ReflectionUtils.setField(field, targetRestaurant, originValue);
            });
    
            repository.save(targetRestaurant);
            return ResponseEntity.ok().build();
    
        } catch (IgnoredPropertyException e) {
            throw new HttpMessageNotReadableException(e.getMessage(), e, servletHttpRequest);
        }
    }

    // @GetMapping("/test")
    // public ResponseEntity<Restaurant> test(String name) {
    //     Restaurant rest = repository.findFirst().orElseThrow();
    //     return ResponseEntity.ok(rest);
    // }
}
