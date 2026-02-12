package com.javamoon.spring_algaworks.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.javamoon.spring_algaworks.domain.model.Restaurant;

@Repository
public interface RestaurantRepository 
    extends CustomJpaRepository<Restaurant, Long>, RestaurantRepositoryQueries,
    JpaSpecificationExecutor<Restaurant> {

    @Override
    @Query("from Restaurant r join fetch r.cuisine left join fetch r.paymentMethods")
    @NonNull
    List<Restaurant> findAll();

    // List<Restaurant> buscarPorTaxa(BigDecimal initialFee, BigDecimal finalFee); 
    public List<Restaurant> buscarPorTaxa(BigDecimal taxaInicial, BigDecimal taxaFinal);
}
