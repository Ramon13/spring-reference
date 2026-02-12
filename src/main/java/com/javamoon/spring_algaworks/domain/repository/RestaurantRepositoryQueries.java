package com.javamoon.spring_algaworks.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.javamoon.spring_algaworks.domain.model.Restaurant;

public interface RestaurantRepositoryQueries {

    List<Restaurant> buscarPorTaxa(BigDecimal taxaInicial, BigDecimal taxaFinal);

    List<Restaurant> findAllWithFreeFee(String name);
}