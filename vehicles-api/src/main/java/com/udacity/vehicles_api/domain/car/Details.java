package com.udacity.vehicles_api.domain.car;

import com.udacity.vehicles_api.domain.manufacturer.Manufacturer;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Declares the additional detail variables for each Car object, along with related methods for access and setting.
 */
@Embeddable @Data @NoArgsConstructor
public class Details {
    @NotBlank private String body;
    @NotBlank private String model;
    @NotNull @ManyToOne private Manufacturer manufacturer;
    private Integer numberOfDoors;
    private String fuelType;
    private String engine;
    private Integer mileage;
    private Integer modelYear;
    private Integer productionYear;
    private String externalColor;
}