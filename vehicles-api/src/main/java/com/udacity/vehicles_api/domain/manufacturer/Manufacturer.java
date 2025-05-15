package com.udacity.vehicles_api.domain.manufacturer;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Declares class to hold car manufacturer information.
 */
@Entity @NoArgsConstructor @AllArgsConstructor @Data
public class Manufacturer {
    @Id private Integer code;
    private String name;
}