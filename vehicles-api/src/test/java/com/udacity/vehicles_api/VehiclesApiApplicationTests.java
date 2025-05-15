package com.udacity.vehicles_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.udacity.vehicles_api.client.maps.MapsClient;
import com.udacity.vehicles_api.client.prices.PriceClient;
import com.udacity.vehicles_api.domain.Condition;
import com.udacity.vehicles_api.domain.Location;
import com.udacity.vehicles_api.domain.car.Car;
import com.udacity.vehicles_api.domain.car.Details;
import com.udacity.vehicles_api.domain.manufacturer.Manufacturer;
import com.udacity.vehicles_api.domain.manufacturer.ManufacturerRepository;
import com.udacity.vehicles_api.service.CarService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootTest
class VehiclesApiApplicationTests {

	private final ManufacturerRepository manufacturerRepository;
    private final CarService carService;
    private final PriceClient priceClient;
    private final MapsClient mapsClient;

	@Test
	void contextLoads() {
	}

	@Test //* Does not use any proper mockito checks, but does test the functionality of the Vehicles API. Check for error messages. */
    void testCarCreationAndEnrichment() {
        manufacturerRepository.save(new Manufacturer(100, "Audi"));
        manufacturerRepository.save(new Manufacturer(101, "Ford"));
        manufacturerRepository.save(new Manufacturer(102, "BMW"));

        Manufacturer audi = manufacturerRepository.findById(100).orElseThrow();
        Manufacturer ford = manufacturerRepository.findById(101).orElseThrow();
        Manufacturer bmw  = manufacturerRepository.findById(102).orElseThrow();

        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();

        car1.setCondition(Condition.NEW);
        car2.setCondition(Condition.NEW);
        car3.setCondition(Condition.NEW);

        car1.setDetails(new Details("SUV", "X5", bmw, 4, "Diesel", "3.0L V6", 20000, 2021, 2021, "White"));
        car2.setDetails(new Details("SUV", "X5", audi, 4, "Diesel", "3.0L V6", 20000, 2021, 2021, "White"));
        car3.setDetails(new Details("SUV", "X5", ford, 4, "Diesel", "3.0L V6", 20000, 2021, 2021, "White"));

        Location loc1 = mapsClient.getAddress(new Location(12.3456, -65.4321));
        Location loc2 = mapsClient.getAddress(new Location(-78.9012, 21.0987));
        Location loc3 = mapsClient.getAddress(new Location(34.5678, -87.6543));

        car1.setLocation(loc1);
        car2.setLocation(loc2);
        car3.setLocation(loc3);

        Car saved1 = carService.save(car1);
        Car saved2 = carService.save(car2);
        Car saved3 = carService.save(car3);

        saved1.setPrice(priceClient.getPrice(saved1.getId()));
        saved2.setPrice(priceClient.getPrice(saved2.getId()));
        saved3.setPrice(priceClient.getPrice(saved3.getId()));
    }
}
