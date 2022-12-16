package com.restaurant.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.ApiResponse;
import com.restaurant.dto.MenuDTO;
import com.restaurant.service.MenuService;

@RestController
@RequestMapping("/menus")
public class MenuController {

	@Autowired
	private MenuService menuService;

	/**
	 * Add Menu service url : /menu method : Post
	 * 
	 * @param menuDto
	 * @return MenuDto {@link com.restaurant.dto.MenuDTO}
	 */
	@PostMapping
	public ResponseEntity<MenuDTO> createMenu(@Valid @RequestBody MenuDTO menuDto) {
		MenuDTO menu2 = menuService.createMenu(menuDto);
		return new ResponseEntity<>(menu2, HttpStatus.CREATED);
	}

	/**
	 * Update menu by id service url: /menu/id method : PUT
	 * 
	 * @param id
	 * @param menuDto
	 * @return Updated MenuDto {@link com.restaurant.dto.MenuDTO}
	 */
	@PutMapping("/{menuId}")
	public ResponseEntity<MenuDTO> updateMenu(@RequestBody MenuDTO menuDto, @PathVariable long menuId) {
		return ResponseEntity.ok(menuService.updateMenu(menuDto, menuId));
	}

	/**
	 * Delete menu by id Method : DELETE Service url: /menu/id
	 * 
	 * @param id
	 * 
	 */
	@DeleteMapping("/{menuId}")
	public ResponseEntity<ApiResponse> deleteMenu(@PathVariable long menuId) {
		menuService.deleteMenu(menuId);
		return new ResponseEntity<>(new ApiResponse("Menu delete successfully", true), HttpStatus.OK);
	}

	/**
	 * get list of menus Service url: /menu method : GET
	 * 
	 * @return list of MenuDtos {@link com.restaurant.dto.MenuDTO}
	 */
	@GetMapping
	public ResponseEntity<List<MenuDTO>> getAllMenu() {
		List<MenuDTO> menu = menuService.getAllMenus();
		return new ResponseEntity<>(menu, HttpStatus.OK);
	}
	
	/**
	 * get list of menus whose satisfied filter condition Service url: /menu method : GET
	 * 
	 * @return list of MenuDtos {@link com.restaurant.dto.MenuDTO}
	 */
	@GetMapping("/filter")
	public ResponseEntity<List<MenuDTO>> menuByFilter(@RequestParam(defaultValue = "20")float price) {
		List<MenuDTO> menu = menuService.menusByFilter(price);
		return new ResponseEntity<>(menu, HttpStatus.OK);
	}

	/**
	 * get detail of Menu by id Service url: /menu/id method: GET
	 * 
	 * @param id
	 * @return MenuDto of particular id {@link com.restaurant.dto.MenuDTO}
	 */
	@GetMapping("/{menuId}")
	public ResponseEntity<MenuDTO> getMenuById(@PathVariable long menuId) {
		return ResponseEntity.ok(menuService.getMenuById(menuId));
	}

	/**
	 * get details of menu isDelted or notDeleted service url :/menu/
	 * 
	 * @param isDeleted=true or false
	 * @return list of menus
	 */
	@GetMapping("/filtermenus")
	public ResponseEntity<List<MenuDTO>> findAll(
			@RequestParam(value = "isDeleted", required = false, defaultValue = "false") boolean isDeleted) {
		List<MenuDTO> menuDtos = menuService.findAllFilter(isDeleted);
		return new ResponseEntity<>(menuDtos, HttpStatus.OK);
	}

}
