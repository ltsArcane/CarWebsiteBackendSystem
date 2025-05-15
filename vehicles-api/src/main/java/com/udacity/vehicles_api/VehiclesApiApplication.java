package com.udacity.vehicles_api;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.reactive.function.client.WebClient;

import com.udacity.vehicles_api.domain.car.Car;
import com.udacity.vehicles_api.domain.car.Details;
import com.udacity.vehicles_api.domain.manufacturer.Manufacturer;
import com.udacity.vehicles_api.domain.manufacturer.ManufacturerRepository;
import com.udacity.vehicles_api.client.maps.MapsClient;
import com.udacity.vehicles_api.client.prices.PriceClient;
import com.udacity.vehicles_api.domain.Condition;
import com.udacity.vehicles_api.domain.Location;
import com.udacity.vehicles_api.service.CarService;

@SpringBootApplication
@EnableJpaAuditing
public class VehiclesApiApplication {
	public static void main(String[] args) { SpringApplication.run(VehiclesApiApplication.class, args); }

	/**
     * Initializes the car manufacturers available to the Vehicle API.
     * @param repository where the manufacturer information persists.
     * @return the car manufacturers to add to the related repository
     */

    @Bean CommandLineRunner loadTestCars(ManufacturerRepository repository, CarService carService, PriceClient priceClient, MapsClient mapsClient) {
        return args -> {
            repository.save(new Manufacturer(100, "Audi"));
            repository.save(new Manufacturer(101, "Ford"));
            repository.save(new Manufacturer(102, "BMW"));

            Manufacturer audi = repository.findById(100).orElseThrow();
            Manufacturer ford = repository.findById(101).orElseThrow();
            Manufacturer bmw = repository.findById(102).orElseThrow();

            Car beforeSaveCar1 = new Car();
            Car beforeSaveCar2 = new Car();
            Car beforeSaveCar3 = new Car();

            beforeSaveCar1.setCondition(Condition.NEW);
            beforeSaveCar2.setCondition(Condition.NEW);
            beforeSaveCar3.setCondition(Condition.NEW);

            beforeSaveCar1.setDetails(new Details("SUV", "X5", bmw, 4, "Diesel", "3.0L V6", 20000, 2021, 2021, "White"));
            beforeSaveCar2.setDetails(new Details("SUV", "X5", audi, 4, "Diesel", "3.0L V6", 20000, 2021, 2021, "White"));
            beforeSaveCar3.setDetails(new Details("SUV", "X5", ford, 4, "Diesel", "3.0L V6", 20000, 2021, 2021, "White"));

            Location location1 = new Location(12.3456, -65.4321);
            Location location2 = new Location(-78.9012, 21.0987);
            Location location3 = new Location(34.5678, -87.6543);

            location1 = mapsClient.getAddress(location1);
            location2 = mapsClient.getAddress(location2);
            location3 = mapsClient.getAddress(location3);

            beforeSaveCar1.setLocation(location1);
            beforeSaveCar2.setLocation(location2);
            beforeSaveCar3.setLocation(location3);

            Car afterSaveCar1 = carService.save(beforeSaveCar1);
            Car afterSaveCar2 = carService.save(beforeSaveCar2);
            Car afterSaveCar3 = carService.save(beforeSaveCar3);

            Long id1 = afterSaveCar1.getId();
            Long id2 = afterSaveCar2.getId();
            Long id3 = afterSaveCar3.getId();

            afterSaveCar1.setPrice(priceClient.getPrice(id1));
            afterSaveCar2.setPrice(priceClient.getPrice(id2));
            afterSaveCar3.setPrice(priceClient.getPrice(id3));
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