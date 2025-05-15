package com.udacity.vehicles_api.domain.car;

import com.udacity.vehicles_api.domain.Condition;
import com.udacity.vehicles_api.domain.Location;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Declares the Car class, related variables and methods.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class Car {
    @Schema(hidden = true) @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Schema(hidden = true) @CreatedDate private LocalDateTime createdAt;
    @Schema(hidden = true) @LastModifiedDate private LocalDateTime modifiedAt;
    @Schema(example = "NEW") @NotNull @Enumerated(EnumType.STRING) private Condition condition;
    @Valid @Embedded private Details details = new Details();
    @Schema(example = "{\"lat\": 0.0, \"lon\": 0.0}") @Valid @Embedded private Location location = new Location(0d, 0d);
    @Schema(hidden = true) @Transient private String price;

}