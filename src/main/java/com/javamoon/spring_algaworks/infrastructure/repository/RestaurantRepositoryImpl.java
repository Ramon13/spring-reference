package com.javamoon.spring_algaworks.infrastructure.repository;

import static com.javamoon.spring_algaworks.infrastructure.repository.spec.RestaurantSpec.withFreeFee;
import static com.javamoon.spring_algaworks.infrastructure.repository.spec.RestaurantSpec.withSimilarName;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.javamoon.spring_algaworks.domain.model.Restaurant;
import com.javamoon.spring_algaworks.domain.repository.RestaurantRepository;
import com.javamoon.spring_algaworks.domain.repository.RestaurantRepositoryQueries;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

    @Autowired
    private EntityManager manager;

    @Autowired @Lazy
    private RestaurantRepository repository;

    @Override
    public List<Restaurant> buscarPorTaxa(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        var jpql = new StringBuilder();
        jpql.append("from Restaurant where 1 = 1 ");

        Map<String, BigDecimal> parameters = new HashMap<>();

        if (taxaInicial != null) {
            jpql.append("and fee >= :taxaInicial");
            parameters.put("taxaInicial", taxaInicial);
        }

        if (taxaFinal != null) {
            jpql.append("and fee <= :taxaFinal");
            parameters.put("taxaFinal", taxaFinal);
        }

        TypedQuery<Restaurant> query = manager.createQuery(jpql.toString(), Restaurant.class);

        parameters.forEach((key, value) -> query.setParameter(key, value));
        return query.getResultList();
    }

    @Override
    public List<Restaurant> findAllWithFreeFee(String name) {
        return repository.findAll(withFreeFee().and(withSimilarName(name)));
    }
}
