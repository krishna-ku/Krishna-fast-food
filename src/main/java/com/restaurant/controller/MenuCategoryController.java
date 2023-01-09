package com.restaurant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.ApiResponse;
import com.restaurant.dto.MenuCategoryDTO;
import com.restaurant.service.MenuCategoryService;

@RestController
@RequestMapping("/menucategories")
public class MenuCategoryController {

	@Autowired
	private MenuCategoryService menuCategoryService;

	/**
	 * Add MenuCategory service url : /menucategories method : Post
	 * @param menuCategoryDTO
	 * @return menuCategoryDto
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@PostMapping
	public ResponseEntity<MenuCategoryDTO> createCategory(@RequestBody MenuCategoryDTO menuCategoryDTO) {

		MenuCategoryDTO category = menuCategoryService.createCategory(menuCategoryDTO);

		return new ResponseEntity<>(category, HttpStatus.CREATED);
	}
	/**
	 * Update menuCategory by id service url: /menucategories/id method : PUT
	 * @param menuCategoryDTO
	 * @param menuCategoryId
	 * @return menuCategoryDTO
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@PutMapping("/{menuCategoryId}")
	public ResponseEntity<MenuCategoryDTO> updateMenuCategory(@RequestBody MenuCategoryDTO menuCategoryDTO,@PathVariable long menuCategoryId){
		
		return ResponseEntity.ok(menuCategoryService.updateCategory(menuCategoryDTO, menuCategoryId));
	}
	/**
	 * Delete menuCategory by id Method : DELETE Service url: /menucategories/id method : DELETE
	 * 
	 * @param id
	 * 
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@DeleteMapping("/{menuCategoryId}")
	public ResponseEntity<ApiResponse> deleteMenuCategory(@PathVariable long menuCategoryId){
		
		menuCategoryService.deleteCategory(menuCategoryId);
		return new ResponseEntity<>(new ApiResponse("Menu category is deleted successfully",true),HttpStatus.OK);
	}
	
	/**
	 * get list of menuCategories Service url: /menucategories method : GET
	 * 
	 * @return list of MenuDtos {@link com.restaurant.dto.MenuDTO}
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping
	public ResponseEntity<List<MenuCategoryDTO>> getAllMenuCategories(){
		
		List<MenuCategoryDTO> menusCategories = menuCategoryService.getAllMenusCategories();
		
		return new ResponseEntity<>(menusCategories,HttpStatus.OK);
	}
	/**
	 * filter menuCategories on basis of name and deleted
	 * 
	 * @param menuCategoryDTO
	 * @return list<menuCategoryDTO>
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping("/filer")
	public ResponseEntity<List<MenuCategoryDTO>> getFilterMenuCategories(@RequestBody MenuCategoryDTO menuCategoryDTO){
		
		List<MenuCategoryDTO> filterMenuCategories = menuCategoryService.filterMenuCategories(menuCategoryDTO);
		
		return new ResponseEntity<>(filterMenuCategories,HttpStatus.OK);
		
	}

}
