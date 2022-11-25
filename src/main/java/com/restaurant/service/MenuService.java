package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.MenuDto;

public interface MenuService {

	// create user
	MenuDto createMenu(MenuDto menu);

	// update user
	MenuDto updateMenu(MenuDto menuDto, Long menuId);

	// delete user
	void deleteMenu(long menuId);

	// get users
	List<MenuDto> getAllMenus();

	// get user
	MenuDto getMenuById(Long menuId);
	
	//get menus deleted or undeleted by header
	List<MenuDto> findAllFilter(boolean isDeleted);

}
