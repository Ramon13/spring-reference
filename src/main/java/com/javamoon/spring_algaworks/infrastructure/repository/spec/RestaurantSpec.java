package com.javamoon.spring_algaworks.infrastructure.repository.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.javamoon.spring_algaworks.domain.model.Restaurant;

public class RestaurantSpec {

    public static Specification<Restaurant> withFreeFee() {
        return (root, query, builder) -> {
            return builder.equal(root.get("fee"), BigDecimal.ZERO);
        };
    }

    public static Specification<Restaurant> withSimilarName(String name) {
        return (root, query, builder) -> {
            return builder.like(root.get("name"), "%" + name + "%");
        };
    }
}
