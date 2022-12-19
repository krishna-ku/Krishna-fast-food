package com.restaurant.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.restaurant.dto.MenuDTO;

public interface MenuService {

	// create user
	MenuDTO createMenu(MenuDTO menu);

	// update user
	MenuDTO updateMenu(MenuDTO menuDto, Long menuId);

	// delete user
	void deleteMenu(long menuId);

	// get users
	List<MenuDTO> getAllMenus();

	// get user
	MenuDTO getMenuById(Long menuId);
	
	//get menus deleted or undeleted by header
	List<MenuDTO> findAllFilter(boolean isDeleted);
	
	//get menus by filter
	List<MenuDTO> menusByFilter(float price);
	
	//save data from excel file
	void save(MultipartFile file);

}
