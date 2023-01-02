package com.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.restaurant.entity.Menu;

public interface MenuRepo extends JpaRepository<Menu, Long>,JpaSpecificationExecutor<Menu> {

//	Menu findByName(String name);

	@Query("select m from Menu m where m.price<=:price")
	List<Menu> menuItemsByFilter(float price);
	
	@Query("select m from Menu m where m.name=:name")
	Menu findByName(String name);

}
