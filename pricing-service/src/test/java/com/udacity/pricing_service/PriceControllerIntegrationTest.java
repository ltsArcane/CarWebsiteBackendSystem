package com.udacity.pricing_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.containsString;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.pricing_service.entities.Price;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
public class PriceControllerIntegrationTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Test
    void testGetPriceEndpoint() throws Exception {
        Long vehicleId = 1L;
        Price price = new Price(vehicleId, "USD", new BigDecimal("10000.00"));

        mockMvc.perform(post("/prices").contentType("application/json").content(objectMapper.writeValueAsString(price)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/prices/" + vehicleId)));
    
        mockMvc.perform(get("/prices/{id}", vehicleId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json")) // HATEOAS
                .andExpect(jsonPath("$.currency").value("USD"))
                .andExpect(jsonPath("$.price").value(10000.00))
                .andExpect(jsonPath("$._links.self.href").value(containsString("/prices/" + vehicleId)));
    }
}