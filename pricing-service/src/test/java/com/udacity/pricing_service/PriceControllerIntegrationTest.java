package com.udacity.pricing_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.containsString;
import com.udacity.pricing_service.entities.Price;
import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class PriceControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private JacksonTester<Price> json;

    @Test
    void testGetPriceEndpoint() throws Exception {
        Price price = new Price(1L, "USD", new BigDecimal("10000.00"));

        mockMvc.perform(post("/prices").contentType("application/json").content(json.write(price).getJson()))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/prices/" + price.getVehicleId())));
    
        mockMvc.perform(get("/prices/{id}", price.getVehicleId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json")) // HATEOAS
                .andExpect(jsonPath("$.currency").value(price.getCurrency()))
                .andExpect(jsonPath("$.price").value(price.getPrice()))
                .andExpect(jsonPath("$._links.self.href").value(containsString("/prices/" + price.getVehicleId())));
    }
}