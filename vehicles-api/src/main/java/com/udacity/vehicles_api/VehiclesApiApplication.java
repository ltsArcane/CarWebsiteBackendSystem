package com.udacity.vehicles_api;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.reactive.function.client.WebClient;


@SpringBootApplication
@EnableJpaAuditing
public class VehiclesApiApplication {
	public static void main(String[] args) { SpringApplication.run(VehiclesApiApplication.class, args); }

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