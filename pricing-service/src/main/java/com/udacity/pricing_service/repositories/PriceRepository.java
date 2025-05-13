package com.udacity.pricing_service.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.udacity.pricing_service.entities.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
}