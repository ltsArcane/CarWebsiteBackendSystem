package com.udacity.vehicles_api.domain.car;

import com.udacity.vehicles_api.domain.Condition;
import com.udacity.vehicles_api.domain.Location;
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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @CreatedDate private LocalDateTime createdAt;
    @LastModifiedDate private LocalDateTime modifiedAt;
    @NotNull @Enumerated(EnumType.STRING) private Condition condition;
    @Valid @Embedded private Details details = new Details();
    @Valid @Embedded private Location location = new Location(0d, 0d);
    @Transient private String price;
}