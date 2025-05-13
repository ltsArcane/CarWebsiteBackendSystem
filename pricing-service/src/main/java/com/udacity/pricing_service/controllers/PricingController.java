package com.udacity.pricing_service.controllers;

import com.udacity.pricing_service.entities.Price;
import com.udacity.pricing_service.exceptions.PriceException;
import com.udacity.pricing_service.service.PricingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/services/price")
public class PricingController {
    @GetMapping
    public Price get(@RequestParam Long vehicleId) { // /services/price?vehicleId=123
        try { return PricingService.getPrice(vehicleId); } 
        catch (PriceException e) { throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Price Not Found", e); }
    }
}
