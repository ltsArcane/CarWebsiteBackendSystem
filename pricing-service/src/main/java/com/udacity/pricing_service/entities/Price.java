package com.udacity.pricing_service.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Data @NoArgsConstructor @AllArgsConstructor @Entity
public class Price {
    @Id private Long vehicleId;
    private String currency;
    private BigDecimal price;
}