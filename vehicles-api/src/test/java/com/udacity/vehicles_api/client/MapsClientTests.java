package com.udacity.vehicles_api.client;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.udacity.vehicles_api.api.CarControllerTest;
import com.udacity.vehicles_api.client.maps.MapsClient;
import com.udacity.vehicles_api.domain.Location;
import com.udacity.vehicles_api.domain.car.Car;
import com.udacity.vehicles_api.service.CarService;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class MapsClientTests {
    @Autowired private MockMvc mvc;
    @Autowired private JacksonTester<Car> json;
    @MockitoBean private MapsClient mapsClient;
    @MockitoBean private CarService carService;

    @BeforeEach
    public void setup() {
        Car car = CarControllerTest.getCar();
        car.setId(1L);
        given(carService.save(any())).willReturn(car);
        given(carService.findById(any())).willReturn(car);
        given(carService.list()).willReturn(Collections.singletonList(car));

        Location location = new Location(car.getLocation().getLat(), car.getLocation().getLon());
        location.setAddress("123 Main St"); location.setCity("Springfield"); location.setState("IL"); location.setZip("12345");
        given(mapsClient.getAddress(car.getLocation())).willReturn(location);
    }

    @Test
    public void testMapsClient() throws Exception {
        Car car = carService.list().get(0);
        Location location = mapsClient.getAddress(car.getLocation());
        car.setLocation(location);

        mvc.perform(put("/cars/{id}", car.getId())
                .contentType("application/json")
                .content(json.write(car).getJson()))
                .andExpect(status().isOk());

        mvc.perform(get("/cars/{id}", car.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location").value(location));
    }
}