package com.javamoon.spring_algaworks.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javamoon.spring_algaworks.domain.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

}
