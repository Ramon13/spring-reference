package com.javamoon.spring_algaworks.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javamoon.spring_algaworks.domain.model.User;
import com.javamoon.spring_algaworks.domain.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping
    public ResponseEntity<List<User>> listAll() {
        List<User> users = repository.findAll();

        return ResponseEntity.ok(users);
    }
}
