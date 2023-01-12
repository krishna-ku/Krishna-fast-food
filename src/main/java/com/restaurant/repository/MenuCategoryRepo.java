package com.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.restaurant.dto.MenuCategoryDTO;
import com.restaurant.entity.MenuCategory;

public interface MenuCategoryRepo extends JpaRepository<MenuCategory, Long>,JpaSpecificationExecutor<MenuCategory> {
	
	@Query("select m from MenuCategory m where m.name=:name")
	MenuCategoryDTO findByName(String name);

}
