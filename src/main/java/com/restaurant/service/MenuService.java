package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.MenuDto;

public interface MenuService {

	// create user
	MenuDto createMenu(MenuDto menu);

	// update user
	MenuDto updateMenu(MenuDto menuDto, Long id);

	// delete user
	void deleteMenu(long id);

	// get users
	List<MenuDto> getAllMenus();

	// get user
	MenuDto getMenuById(Long id);

}
