package com.udacity.vehicles_api.client;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.udacity.vehicles_api.api.CarControllerTest;
import com.udacity.vehicles_api.client.prices.PriceClient;
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

/**
 * Implements testing of the CarController class.
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class PriceClientTests {
    @Autowired private MockMvc mvc;
    @Autowired private JacksonTester<Car> json;
    @MockitoBean private PriceClient priceClient;
    @MockitoBean private CarService carService;

    /**
     * Creates pre-requisites for testing, such as an example car.
     */
    @BeforeEach
    public void setup() {
        Car car = CarControllerTest.getCar();
        car.setId(1L);
        given(carService.save(any())).willReturn(car);
        given(carService.findById(any())).willReturn(car);
        given(carService.list()).willReturn(Collections.singletonList(car));

        given(priceClient.getPrice(anyLong())).willReturn("USD 12345.67");

    }

    @Test
    public void testPriceClient() throws Exception {
        Car car = carService.list().get(0);
        String price = priceClient.getPrice(car.getId());
        car.setPrice(price);

        mvc.perform(put("/cars/{id}", car.getId())
                .contentType("application/json")
                .content(json.write(car).getJson()))
                .andExpect(status().isOk());

        mvc.perform(get("/cars/{id}", car.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(price));
    }
}