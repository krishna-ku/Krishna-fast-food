package com.restaurant.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.dto.Keywords;
import com.restaurant.dto.MenuDto;
import com.restaurant.entity.Menu;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.repository.MenuRepo;
import com.restaurant.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepo menuRepo;

	/**
	 * add Menu.
	 * @param menuDto
	 * @return MenuDto
	 * @see com.restaurant.dto.MenuDto
	 */
	@Override
	public MenuDto createMenu(MenuDto menuDto) {
		
//		Menu findById = this.menuRepo.findById(menuDto.getId()).orElseThrow(null)

//		if(findById==null) {
		Menu menu = this.dtoToMenu(menuDto);
		Menu saveMenu = this.menuRepo.save(menu);
		return this.menuToDto(saveMenu);
//		}
//		else {
//			return null;
//		}
	}

	/**
	 * update Menu
	 * @param menuDto
	 * @param id
	 * @return updated menuDto
	 * @see com.restaurant.dto.MenuDto
	 */
	@Override
	public MenuDto updateMenu(MenuDto menuDto, Long id) {

		Menu updatedMenu = menuRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(Keywords.MENU, Keywords.MENU_ID, id));
		
		
		
		if(!StringUtils.isEmpty(menuDto.getName())) {
		updatedMenu.setName(menuDto.getName());}
		
		if(!StringUtils.isEmpty(Float.toString(menuDto.getPrice()))) {
		updatedMenu.setPrice(menuDto.getPrice());}
		
		if(!StringUtils.isEmpty(menuDto.getDescription())) {
		updatedMenu.setDescription(menuDto.getDescription());}
		
		Menu udateMenu = menuRepo.save(updatedMenu);

		return this.menuToDto(udateMenu);
	}

	/**
	 * delete Menu
	 * @param id
	 * @return void
	 */
	
	@Override
	public void deleteMenu(long id) {

		Menu menu = this.menuRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(Keywords.MENU, Keywords.MENU_ID, id));

		menuRepo.delete(menu);
	}

	/**
	 * return all menus
	 * 
	 * @return list of menuDtos
	 * @see com.restaurant.dto.MenuDto
	 */
	@Override
	public List<MenuDto> getAllMenus() {

		List<Menu> menus = menuRepo.findAll();
		
//		List<MenuDto> menuDtos = menus.stream().map(menu -> menuToDto(menu)).collect(Collectors.toList());

		return menus.stream().map(this::menuToDto).collect(Collectors.toList());
	}

	/**
	 * return menu by id
	 * @param id
	 * @return menuDto by id
	 * @see com.restaurant.dto.MenuDto
	 */
	@Override
	public MenuDto getMenuById(Long id) {

		Menu menu = menuRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(Keywords.MENU, Keywords.MENU_ID, id));

		return menuToDto(menu);
	}

	/**
	 * convert menuDto into menu
	 * @param menuDto
	 * @return menu
	 * @see com.restaurant.entity.Menu
	 */
	private Menu dtoToMenu(MenuDto menuDto) {
		Menu menu = new Menu();
		//menu.setId(menuDto.getId());
		menu.setName(menuDto.getName());
		menu.setPrice(menuDto.getPrice());
		menu.setDescription(menuDto.getDescription());
		return menu;
	}

	/**
	 * convert menu into menuDto
	 * @param menu
	 * @return menuDto
	 * @see com.restaurant.dto.MenuDto
	 */
	private MenuDto menuToDto(Menu menu) {
		MenuDto menuDto = new MenuDto();
		menuDto.setId(menu.getId());
		menuDto.setName(menu.getName());
		menuDto.setPrice(menu.getPrice());
		menuDto.setDescription(menu.getDescription());
		return menuDto;
	}

}
