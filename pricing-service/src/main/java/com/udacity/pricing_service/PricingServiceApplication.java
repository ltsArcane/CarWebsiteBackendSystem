package com.udacity.pricing_service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.udacity.pricing_service.entities.Price;
import com.udacity.pricing_service.repositories.PriceRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class PricingServiceApplication {
	public static void main(String[] args) { SpringApplication.run(PricingServiceApplication.class, args); }

	@Bean CommandLineRunner preloadPrices(PriceRepository repository) {
		return args -> { LongStream.range(1, 20).forEach(i -> { Price price = new Price(i, "USD", randomPrice()); repository.save(price); }); };
	}

	private BigDecimal randomPrice() {
		return new BigDecimal(ThreadLocalRandom.current().nextDouble(1, 5)).multiply(BigDecimal.valueOf(5000)).setScale(2, RoundingMode.HALF_UP);
	}
}