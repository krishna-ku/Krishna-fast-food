package com.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.entity.Restaurant;

public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {

}
