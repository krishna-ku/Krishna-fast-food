package com.restaurant.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.restaurant.dto.MenuDTO;
import com.restaurant.dto.PagingDTO;

public interface MenuService {

	// create user
	MenuDTO createMenu(MenuDTO menu);

	// update user
	MenuDTO updateMenu(MenuDTO menuDto, Long menuId);

	// delete user
	void deleteMenu(long menuId);

	// get users
	PagingDTO getAllMenus(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

	// save data from excel file
	void saveExcelFile(MultipartFile file);

	// save data from excel file
	void saveCsvFile(MultipartFile uploadMenuFromCsvFile);

	// activate menu
	String activateMenu(long menuId);

	// checkUploadFileIsCsvOrExcel
	void checkUploadFileIsCsvOrExcel(MultipartFile uploadMenuFromFile);

	// filter menus
	List<MenuDTO> filterMenus(MenuDTO menuDTO);

	// upload image in menu
	MenuDTO uploadImage(MultipartFile image, long userId) throws IOException;

	// view image
	void viewImage(String image, HttpServletResponse response) throws IOException;

}
