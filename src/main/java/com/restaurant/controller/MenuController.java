package com.restaurant.controller;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.dto.ApiResponse;
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
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@PostMapping
	public ResponseEntity<MenuDTO> createMenu(@Valid @RequestBody MenuDTO menuDto) {
		MenuDTO newmenu = menuService.createMenu(menuDto);
		return new ResponseEntity<>(newmenu, HttpStatus.CREATED);
	}

	/**
	 * Update menu by id service url: /menus/id method : PUT
	 * 
	 * @param id
	 * @param menuDto
	 * @return Updated MenuDto {@link com.restaurant.dto.MenuDTO}
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
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
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
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
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping
	public ResponseEntity<List<MenuDTO>> getAllMenu() {
		List<MenuDTO> menu = menuService.getAllMenus();
		return new ResponseEntity<>(menu, HttpStatus.OK);
	}

	/**
	 * filter menus on basis of name,price,id and deleted
	 * 
	 * @param menuDTO
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping("/filter")
	public ResponseEntity<List<MenuDTO>> filterMenus(@RequestBody MenuDTO menuDTO) {
		List<MenuDTO> filterMenus = menuService.filterMenus(menuDTO);
		return new ResponseEntity<>(filterMenus, HttpStatus.OK);
	}

	/**
	 * Activate Menu
	 * 
	 * @param userId
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@PutMapping("/activate/{menuId}")
	public ResponseEntity<String> activateUserEntity(@PathVariable long menuId) {
		return ResponseEntity.ok(menuService.activateMenu(menuId));
	}

	/**
	 * check file is excle or no then save that file in Database service url:
	 * /menus/uploadfile method: POST
	 * 
	 * @param file
	 * @return message and status
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@PostMapping("/upload")
	public ResponseEntity<ApiResponse> upload(@RequestParam("file") MultipartFile uploadMenuFromFile) {
		menuService.checkUploadFileIsCsvOrExcel(uploadMenuFromFile);
		return new ResponseEntity<>(new ApiResponse("file is upload successfully and data is saved", true),
				HttpStatus.OK);
	}

}
