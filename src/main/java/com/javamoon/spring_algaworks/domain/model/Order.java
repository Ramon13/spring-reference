package com.javamoon.spring_algaworks.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "f_order")
public class Order {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal subTotal;

    @Column(nullable = false)
    private BigDecimal shippingTax;

    @Column(nullable = false)
    private BigDecimal total;

    @CreationTimestamp
    @Column(nullable = false)
    private String creationDate;

    private LocalDateTime confirmationDate;

    private LocalDateTime cancelDate;

    private LocalDateTime dispatchDate;
    
    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Embedded
    private Address address;

    @Column(nullable = false)
    private OrderStatus status;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items = new ArrayList<>();

    public LocalDateTime getCreationDate() {
        return Optional.ofNullable(creationDate)
            .map(timestamp -> LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .orElse(null);
    }
}
