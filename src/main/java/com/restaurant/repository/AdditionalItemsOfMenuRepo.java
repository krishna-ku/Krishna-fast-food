package com.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.restaurant.entity.AdditionalItemsOfMenu;
import com.restaurant.entity.Menu;

public interface AdditionalItemsOfMenuRepo extends JpaRepository<AdditionalItemsOfMenu, Long> {
	
	@Query("select m from AdditionalItemsOfMenu m where m.name=:name")
	AdditionalItemsOfMenu findByName(String name);
	
	
}
