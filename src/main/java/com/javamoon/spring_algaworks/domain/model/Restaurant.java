package com.javamoon.spring_algaworks.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurant {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private BigDecimal fee;

    // @JsonIgnore
    // @JsonIgnoreProperties("hibernateLazyInitializer")
    @ManyToOne
    @JoinColumn(name = "cuisine_id", nullable = false)
    private Cuisine cuisine;

    // @JsonIgnoreProperties("hibernateLazyInitializer")
    @ManyToOne
    @JoinColumn(name = "address_city_id", nullable = false)
    private City city;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "restaurant_payment_method",
        joinColumns = @JoinColumn(name = "restaurant_id"),
        inverseJoinColumns = @JoinColumn(name = "payment_id")
    )
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    @JsonIgnore
    @Embedded
    private Address address;

    @CreationTimestamp
    @Column(name = "creation_timestamp", nullable = false)
    private String creationTimestamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp", nullable = false)
    private String updateTimestamp;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    private List<Item> items;

    public LocalDateTime getCreationTimestamp() {
        return Optional.ofNullable(creationTimestamp)
            .map(timestamp -> LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .orElse(null);
    }

    public LocalDateTime getUpdateTimestamp() {
        return Optional.ofNullable(updateTimestamp)
            .map(timestamp -> LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .orElse(null);
    }
}