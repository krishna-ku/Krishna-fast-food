package com.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.entity.Menu;

public interface MenuRepo extends JpaRepository<Menu, Long>{
	
	Menu findByName(String name);

}
