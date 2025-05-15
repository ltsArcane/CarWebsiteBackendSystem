package com.udacity.vehicles_api.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Stores information about a given location.
 * Latitude and longitude must be provided, while other location information must be gathered each time from the maps API.
 * Since Latitude and Longitude are required, this class only has a required args constructor.
 */
@Embeddable @RequiredArgsConstructor
public class Location {
    @Getter @NotNull private final Double lat;
    @Getter @NotNull private final Double lon;
    @Getter @Setter @Transient private String address;
    @Getter @Setter @Transient private String city;
    @Getter @Setter @Transient private String state;
    @Getter @Setter @Transient private String zip;
}