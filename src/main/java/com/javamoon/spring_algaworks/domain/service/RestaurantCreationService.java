package com.javamoon.spring_algaworks.domain.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.javamoon.spring_algaworks.domain.exception.CityNotFoundException;
import com.javamoon.spring_algaworks.domain.exception.RestaurantNotFoundException;
import com.javamoon.spring_algaworks.domain.model.City;
import com.javamoon.spring_algaworks.domain.model.Cuisine;
import com.javamoon.spring_algaworks.domain.model.Restaurant;
import com.javamoon.spring_algaworks.domain.repository.CityRepository;
import com.javamoon.spring_algaworks.domain.repository.RestaurantRepository;

@Service
public class RestaurantCreationService {

    @Autowired
    private CuisineCreationService cuisineService;

    @Autowired
    private RestaurantRepository repository;

    @Autowired
    private CityRepository cityRepository;

    public Restaurant create(Restaurant restaurant) {
        Long cuisineId = restaurant.getCuisine().getId();
        Cuisine cuisine = cuisineService.findOrElseThrow(cuisineId);

        Long cityId = restaurant.getCity().getId();
        if (Objects.nonNull(cityId)) {
            City city = cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));
            restaurant.setCity(city);
        }

        restaurant.setCuisine(cuisine);
        return repository.save(restaurant);
    }

    public Restaurant findOrElseThrow(@NonNull Long restaurantId) {
        return repository.findById(restaurantId)
            .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
    }
}
