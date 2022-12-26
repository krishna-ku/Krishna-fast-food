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
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.dto.ApiResponse;
import com.restaurant.dto.ExcelHelper;
import com.restaurant.dto.MenuDTO;
import com.restaurant.service.MenuService;

@RestController
@RequestMapping("/menus")
public class MenuController {

	@Autowired
	private MenuService menuService;

	/**
	 * Add Menu service url : /menus method : Post
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
	 * Update menu by id service url: /menus/id method : PUT
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
	 * Delete menu by id Method : DELETE Service url: /menus/id method : DELETE
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
	 * get list of menus Service url: /menus method : GET
	 * 
	 * @return list of MenuDtos {@link com.restaurant.dto.MenuDTO}
	 */
	@GetMapping
	public ResponseEntity<List<MenuDTO>> getAllMenu() {
		List<MenuDTO> menu = menuService.getAllMenus();
		return new ResponseEntity<>(menu, HttpStatus.OK);
	}

	/**
	 * get lists of menus by maxPrice Service url: /menus/filter/byPrice=20 it will
	 * give all menus smaller then 20 or equal then 20 method : GET
	 * 
	 * @return list of MenuDtos {@link com.restaurant.dto.MenuDTO}
	 */
	@GetMapping("/filter")
	public ResponseEntity<List<MenuDTO>> menuByFilter(@RequestParam(defaultValue = "20") float byPrice) {
		List<MenuDTO> menu = menuService.filterMenusByPrice(byPrice);
		return new ResponseEntity<>(menu, HttpStatus.OK);
	}

	/**
	 * get detail of Menu by id Service url: /menus/id method: GET
	 * 
	 * @param id
	 * @return MenuDto of particular id {@link com.restaurant.dto.MenuDTO}
	 */
	@GetMapping("/{menuId}")
	public ResponseEntity<MenuDTO> getMenuById(@PathVariable long menuId) {
		return ResponseEntity.ok(menuService.getMenuById(menuId));
	}

	/**
	 * Activate Menu
	 * 
	 * @param userId
	 * @return
	 */
	@PutMapping("/activate/{menuId}")
	public ResponseEntity<String> activateUserEntity(@PathVariable long menuId) {
		return ResponseEntity.ok(menuService.activateMenu(menuId));
	}

	/**
	 * get details of menu isDelted or notDeleted service url :/menus/filtermenus
	 * method: GET
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

	/**
	 * check file is excle or no then save that file in Database service url:
	 * /menus/uploadfile method: POST
	 * 
	 * @param file
	 * @return message and status
	 */
	@PostMapping("/upload")
	public ResponseEntity<ApiResponse> upload(@RequestParam("file") MultipartFile uploadMenuFromFile) {
		if (ExcelHelper.checkExcelFormat(uploadMenuFromFile)) {
			this.menuService.save(uploadMenuFromFile);
			return new ResponseEntity<>(
					new ApiResponse("your excel file is uploaded and data is saved in database", true), HttpStatus.OK);
		} else if (ExcelHelper.checkCSVFormat(uploadMenuFromFile)) {
			this.menuService.saveCsv(uploadMenuFromFile);
			return new ResponseEntity<>(
					new ApiResponse("your csv file is uploaded and data is saved in database", true), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ApiResponse("Please upload excel or csv file", false),
					HttpStatus.BAD_REQUEST);
		}
	}

}
