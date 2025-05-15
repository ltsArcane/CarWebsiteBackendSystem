package com.udacity.vehicles_api.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Stores information about a given location.
 * Latitude and longitude must be provided, while other location information must be gathered each time from the maps API.
 * Since Latitude and Longitude are required, this class only has a required args constructor.
 */
@Embeddable @Data @NoArgsConstructor
public class Location {
    @NotNull private Double lat;
    @NotNull private Double lon;
    @Transient private String address;
    @Transient private String city;
    @Transient private String state;
    @Transient private String zip;

    public Location(Double lat, Double lon) { this.lat = lat; this.lon = lon; }
}