package com.javamoon.spring_algaworks.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javamoon.spring_algaworks.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
