package com.udacity.boogle_maps.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.boogle_maps.entities.Address;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MockAddressRepository {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Random RNG = new Random();
    private static final List<String> ADDRESSES;

    static {
        try (InputStream in = MockAddressRepository.class.getResourceAsStream("/addresses.json")) {
            ADDRESSES = List.of(MAPPER.readValue(in, String[].class));
        } catch (IOException e) { 
            throw new ExceptionInInitializerError(e); 
        }
    }

    public static Address getRandom() {
        String raw = ADDRESSES.get(RNG.nextInt(ADDRESSES.size()));
        String[] split = raw.split(",", 2);
        String street = split[0].trim();
        String[] locationParts = split[1].trim().split(" ");
        int count = locationParts.length;
        String city = String.join(" ", Arrays.copyOf(locationParts, count - 2));
        String state = locationParts[count - 2];
        String zip = locationParts[count - 1];

        return new Address(street, city, state, zip);
    }
}