package com.restaurant.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.entity.Restaurant;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
	
	@PostMapping
	public Restaurant restaurant(){
		
		Restaurant restaurant=new Restaurant();
		
		return restaurant;
		
	}
	
}
