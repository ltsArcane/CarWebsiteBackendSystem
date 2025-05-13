package com.udacity.pricing_service.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Data @AllArgsConstructor @Entity
public class Price {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String currency;
    private BigDecimal price;
    private Long vehicleId;

    public Price(String currency, BigDecimal price, Long vehicleId) { // Custom constructor.
        this.currency = currency;
        this.price = price;
        this.vehicleId = vehicleId;
    }
}