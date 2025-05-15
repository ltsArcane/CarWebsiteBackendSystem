package com.udacity.vehicles_api;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import com.udacity.vehicles_api.domain.manufacturer.Manufacturer;
import com.udacity.vehicles_api.domain.manufacturer.ManufacturerRepository;

@SpringBootApplication
public class VehiclesApiApplication {
	public static void main(String[] args) { SpringApplication.run(VehiclesApiApplication.class, args); }

	/**
     * Initializes the car manufacturers available to the Vehicle API.
     * @param repository where the manufacturer information persists.
     * @return the car manufacturers to add to the related repository
     */
    @Bean CommandLineRunner initDatabase(ManufacturerRepository repository) {
        return args -> {
            repository.save(new Manufacturer(100, "Audi"));
            repository.save(new Manufacturer(101, "Chevrolet"));
            repository.save(new Manufacturer(102, "Ford"));
            repository.save(new Manufacturer(103, "BMW"));
            repository.save(new Manufacturer(104, "Dodge"));
        };
    }

    @Bean ModelMapper modelMapper() { return new ModelMapper(); }

    @Bean(name="webClientMapsBuilder")
    WebClient.Builder webClientMapsBuilder() { 
        return WebClient.builder().baseUrl("http://localhost:9191");
    }

    @Bean(name = "webClientPricingBuilder") @LoadBalanced
    WebClient.Builder webClientPricingBuilder() { 
        return WebClient.builder().baseUrl("http://pricing-service");
    }
}