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
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.ApiResponse;
import com.restaurant.dto.MenuDto;
import com.restaurant.service.MenuService;

@RestController
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;

	/**
	 * Add Menu
	 * service url : /menu
	 * method : Post
	 * @param menuDto
	 * @return MenuDto {@link com.restaurant.dto.MenuDto}
	 */
	@PostMapping
	public ResponseEntity<MenuDto> createMenu(@Valid @RequestBody MenuDto menuDto) {
		MenuDto menu2 = menuService.createMenu(menuDto);
		return new ResponseEntity<>(menu2, HttpStatus.CREATED);
	}

	/**
	 * Update menu by id
	 * service url: /menu/id
	 * method : PUT
	 * @param id
	 * @param menuDto
	 * @return Updated MenuDto {@link com.restaurant.dto.MenuDto}
	 */
	@PutMapping("/{id}")
	public ResponseEntity<MenuDto> updateMenu(@RequestBody MenuDto menuDto, @PathVariable long id) {
		return ResponseEntity.ok(menuService.updateMenu(menuDto, id));
	}

	/**
	 * Delete menu by id
	 * Method : DELETE
	 * Service url: /menu/id
	 * @param id
	 * 
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteMenu(@PathVariable long id) {
		menuService.deleteMenu(id);
		return new ResponseEntity<>(new ApiResponse("Menu delete successfully",true),HttpStatus.OK);
	}

	/**
	 * get list of menus
	 * Service url: /menu
	 * method :  GET
	 * @return list of MenuDtos {@link com.restaurant.dto.MenuDto}
	 */
	@GetMapping
	public ResponseEntity<List<MenuDto>> getAllMenu() {
		List<MenuDto> menu = menuService.getAllMenus();
		return new ResponseEntity<>(menu, HttpStatus.OK);
	}
	
	/**
	 * get detail of Menu by id
	 * Service url: /menu/id
	 * method: GET
	 *@param id
	 * @return MenuDto of particular id {@link com.restaurant.dto.MenuDto}
	 */
	@GetMapping("/{id}")
	public ResponseEntity<MenuDto> getMenuById(@PathVariable long id) {
		return ResponseEntity.ok(menuService.getMenuById(id));
	}

}
