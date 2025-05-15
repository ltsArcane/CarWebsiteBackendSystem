package com.udacity.vehicles_api.domain.manufacturer;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Declares class to hold car manufacturer information.
 */
@Entity @NoArgsConstructor @AllArgsConstructor @Data
public class Manufacturer {
    @Id @Getter private Integer code;
    @Getter private String name;
}