package com.udacity.vehicles_api.client.prices;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the price of a given vehicle, including currency.
 */
@Data @NoArgsConstructor
public class Price {
    private String currency;
    private BigDecimal price;
    private Long vehicleId;
}