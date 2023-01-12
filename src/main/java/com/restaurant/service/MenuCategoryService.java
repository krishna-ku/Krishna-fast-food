package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.MenuCategoryDTO;
import com.restaurant.entity.MenuCategory;

public interface MenuCategoryService {
	
	//create category
	MenuCategoryDTO createCategory(MenuCategoryDTO menuCategoryDTO);
	
	//update category
	MenuCategoryDTO updateCategory(MenuCategoryDTO menuCategoryDTO,long menuCategoryId);
	
	//delete category
	void deleteCategory(long menuCategoryId);
	
	//get MenuCategoris
	List<MenuCategoryDTO> getAllMenusCategories(Integer pageNumber,Integer pageSize);
	
	//get MenuCategories by filter like delete and name
	List<MenuCategoryDTO> filterMenuCategories(MenuCategoryDTO menuCategoryDTO);
	
	
}
