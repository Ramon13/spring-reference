package com.javamoon.spring_algaworks.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javamoon.spring_algaworks.domain.model.Cuisine;

public interface CuisineRepository extends JpaRepository<Cuisine, Long>, CuisineRepositoryCustom {

    // List<Cuisine> findByName(String name);
}
