package com.udacity.vehicles_api.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.udacity.vehicles_api.domain.Condition;
import com.udacity.vehicles_api.domain.Location;
import com.udacity.vehicles_api.domain.car.Car;
import com.udacity.vehicles_api.domain.car.Details;
import com.udacity.vehicles_api.domain.manufacturer.Manufacturer;
import com.udacity.vehicles_api.service.CarService;
import java.net.URI;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;

/**
 * Implements testing of the CarController class.
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CarControllerTest {
    @Autowired private MockMvc mvc;
    @Autowired private JacksonTester<Car> json;
    @MockitoBean private CarService carService;

    /**
     * Creates pre-requisites for testing, such as an example car.
     */
    @BeforeEach
    public void setup() {
        Car car = getCar();
        car.setId(1L);
        given(carService.save(any())).willReturn(car);
        given(carService.findById(any())).willReturn(car);
        given(carService.list()).willReturn(Collections.singletonList(car));
    }

    /**
     * Tests for successful creation of new car in the system
     * @throws Exception when car creation fails in the system
     */
    @Test
    public void createCar() throws Exception {
        Car car = getCar();
        mvc.perform(
                post(new URI("/cars"))
                        .content(json.write(car).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    /**
     * Tests if the read operation appropriately returns a list of vehicles.
     * @throws Exception if the read operation of the vehicle list fails
     */
    @Test
    public void listCars() throws Exception {

        Car car = carService.list().get(0);
        String path = "$._embedded.carList[0].";

        mvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath(path + "id").value(car.getId()))
                .andExpect(jsonPath(path + "condition").value(car.getCondition().toString()))
                .andExpect(jsonPath(path + "details.manufacturer.name").value(car.getDetails().getManufacturer().getName()))
                .andExpect(jsonPath(path + "details.manufacturer.code").value(car.getDetails().getManufacturer().getCode()))
                .andExpect(jsonPath(path + "details.body").value(car.getDetails().getBody()))
                .andExpect(jsonPath(path + "details.model").value(car.getDetails().getModel()))
                .andExpect(jsonPath(path + "details.engine").value(car.getDetails().getEngine()))
                .andExpect(jsonPath(path + "details.fuelType").value(car.getDetails().getFuelType()))
                .andExpect(jsonPath(path + "details.numberOfDoors").value(car.getDetails().getNumberOfDoors()))
                .andExpect(jsonPath(path + "details.externalColor").value(car.getDetails().getExternalColor()))
                .andExpect(jsonPath(path + "details.mileage").value(car.getDetails().getMileage()))
                .andExpect(jsonPath(path + "details.productionYear").value(car.getDetails().getProductionYear()))
                .andExpect(jsonPath(path + "details.modelYear").value(car.getDetails().getModelYear()))
                .andExpect(jsonPath(path + "location.lat").value(car.getLocation().getLat()))
                .andExpect(jsonPath(path + "location.lon").value(car.getLocation().getLon()))
                .andExpect(jsonPath(path + "_links.self.href").value(containsString("/cars/" + car.getId())));
    }

    /**
     * Tests the read operation for a single car by ID.
     * @throws Exception if the read operation for a single car fails
     */
    @Test
    public void findCar() throws Exception {

        Car car = carService.list().get(0);
        Long vehicleId = car.getId();

        mvc.perform(get("/cars/{id}", vehicleId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value(car.getId()))
                .andExpect(jsonPath("$.condition").value(car.getCondition().toString()))
                .andExpect(jsonPath("$.details.manufacturer.name").value(car.getDetails().getManufacturer().getName()))
                .andExpect(jsonPath("$.details.manufacturer.code").value(car.getDetails().getManufacturer().getCode()))
                .andExpect(jsonPath("$.details.body").value(car.getDetails().getBody()))
                .andExpect(jsonPath("$.details.model").value(car.getDetails().getModel()))
                .andExpect(jsonPath("$.details.engine").value(car.getDetails().getEngine()))
                .andExpect(jsonPath("$.details.fuelType").value(car.getDetails().getFuelType()))
                .andExpect(jsonPath("$.details.numberOfDoors").value(car.getDetails().getNumberOfDoors()))
                .andExpect(jsonPath("$.details.externalColor").value(car.getDetails().getExternalColor()))
                .andExpect(jsonPath("$.details.mileage").value(car.getDetails().getMileage()))
                .andExpect(jsonPath("$.details.productionYear").value(car.getDetails().getProductionYear()))
                .andExpect(jsonPath("$.details.modelYear").value(car.getDetails().getModelYear()))
                .andExpect(jsonPath("$.location.lat").value(car.getLocation().getLat()))
                .andExpect(jsonPath("$.location.lon").value(car.getLocation().getLon()))
                .andExpect(jsonPath("$._links.self.href").value(containsString("/cars/" + vehicleId)));
    }

    /**
     * Tests the deletion of a single car by ID.
     * @throws Exception if the delete operation of a vehicle fails
     */
    @Test
    public void deleteCar() throws Exception {

        Car car = carService.list().get(0);
        Long vehicleId = car.getId();

        mvc.perform(delete("/cars/{id}", vehicleId)).andExpect(status().isNoContent());

        verify(carService, times(1)).delete(vehicleId);
    }

    /**
     * Creates an example Car object for use in testing.
     * @return an example Car object
     */
    public static Car getCar() {
        Car car = new Car();
        car.setLocation(new Location(40.730610, -73.935242));
        Details details = new Details();
        Manufacturer manufacturer = new Manufacturer(101, "Chevrolet");
        details.setManufacturer(manufacturer);
        details.setModel("Impala");
        details.setMileage(32280);
        details.setExternalColor("white");
        details.setBody("sedan");
        details.setEngine("3.6L V6");
        details.setFuelType("Gasoline");
        details.setModelYear(2018);
        details.setProductionYear(2018);
        details.setNumberOfDoors(4);
        car.setDetails(details);
        car.setCondition(Condition.USED);
        return car;
    }
}