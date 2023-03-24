package com.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.entity.AdditionalItemsOfMenu;

public interface AdditionalItemsOfMenuRepo extends JpaRepository<AdditionalItemsOfMenu, Long> {
	
	
}
