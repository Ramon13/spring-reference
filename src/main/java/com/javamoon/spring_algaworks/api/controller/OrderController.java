package com.javamoon.spring_algaworks.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javamoon.spring_algaworks.domain.model.Order;
import com.javamoon.spring_algaworks.domain.repository.OrderRepository;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository repository;

    @GetMapping
    public List<Order> listAll() {
        return repository.findAll();
    }
}
