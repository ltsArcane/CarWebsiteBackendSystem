package com.udacity.vehicles_api.domain.manufacturer;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "100") @Id private Integer code;
    @Schema(example = "Audi") private String name;
}