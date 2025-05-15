package com.udacity.vehicles_api.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.udacity.vehicles_api.domain.car.Car;
import com.udacity.vehicles_api.service.CarService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implements a REST-based controller for the Vehicles API.
 */
@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
class CarController {
    private final CarService carService;
    private final CarResourceAssembler assembler;

    /**
     * Creates a list to store any vehicles.
     * @return list of vehicles
     */
    @GetMapping
    CollectionModel<EntityModel<Car>> list() {
        List<EntityModel<Car>> resources = carService.list().stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(resources, linkTo(methodOn(CarController.class).list()).withSelfRel());
    }

    /**
     * Gets information of a specific car by ID.
     * @param id the id number of the given vehicle
     * @return all information for the requested vehicle
     */
    @GetMapping("/{id}")
    ResponseEntity<?> get(@PathVariable Long id) {
        Car car = carService.findById(id);
        return ResponseEntity.ok(assembler.toModel(car));
    }

    /**
     * Posts information to create a new vehicle in the system.
     * @param car A new vehicle to add to the system.
     * @return response that the new vehicle was added to the system
     * @throws URISyntaxException if the request contains invalid fields or syntax
     */
    @PostMapping
    ResponseEntity<?> post(@Valid @RequestBody Car car) throws URISyntaxException {
        Car savedCar = carService.save(car);
        savedCar = carService.findById(savedCar.getId()); // Triggers Map and Price WebClient calls.
        EntityModel<Car> resource = assembler.toModel(savedCar);
        return ResponseEntity.created(new URI(resource.getRequiredLink("self").getHref())).body(resource);     
    }

    /**
     * Updates the information of a vehicle in the system.
     * @param id The ID number for which to update vehicle information.
     * @param car The updated information about the related vehicle.
     * @return response that the vehicle was updated in the system
     */
    @PutMapping("/{id}")
    ResponseEntity<?> put(@PathVariable Long id, @Valid @RequestBody Car car) {
        car.setId(id);
        Car updatedCar = carService.save(car);
        updatedCar = carService.findById(updatedCar.getId()); // Triggers Map and Price WebClient calls.
        EntityModel<Car> resource = assembler.toModel(updatedCar);
        return ResponseEntity.ok(resource);
    }

    /**
     * Removes a vehicle from the system.
     * @param id The ID number of the vehicle to remove.
     * @return response that the related vehicle is no longer in the system
     */
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
