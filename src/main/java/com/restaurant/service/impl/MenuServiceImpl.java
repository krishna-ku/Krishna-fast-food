package com.restaurant.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.dto.Keywords;
import com.restaurant.dto.MenuDto;
import com.restaurant.entity.Menu;
import com.restaurant.exception.BadRequestException;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.repository.MenuRepo;
import com.restaurant.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepo menuRepo;

	/**
	 * add Menu.
	 * 
	 * @param menuDto
	 * @return MenuDto
	 * @see com.restaurant.dto.MenuDto
	 */
	@Override
	public MenuDto createMenu(MenuDto menuDto) {

		Menu menuExist = this.menuRepo.findByName(menuDto.getName());

		if (menuExist == null) {
			Menu newMenu = new Menu(menuDto);
			return new MenuDto(menuRepo.save(newMenu));
		} else {
			throw new BadRequestException("Menu is already exists");
		}
	}

	/**
	 * update Menu
	 * 
	 * @param menuDto
	 * @param id
	 * @return updated menuDto
	 * @see com.restaurant.dto.MenuDto
	 */
	@Override
	public MenuDto updateMenu(MenuDto menuDto, Long menuId) {

		Menu updatedMenu = menuRepo.findById(menuId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.MENU, Keywords.MENU_ID, menuId));

		if (!StringUtils.isEmpty(menuDto.getName())) {
			updatedMenu.setName(menuDto.getName());
		}

		if (!StringUtils.isEmpty(Float.toString(menuDto.getPrice()))) {
			updatedMenu.setPrice(menuDto.getPrice());
		}

		if (!StringUtils.isEmpty(menuDto.getDescription())) {
			updatedMenu.setDescription(menuDto.getDescription());
		}

		return new MenuDto(menuRepo.save(updatedMenu));
	}

	/**
	 * delete Menu
	 * 
	 * @param id
	 * @return void
	 */

	@Override
	public void deleteMenu(long menuId) {

		Menu menu = this.menuRepo.findById(menuId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.MENU, Keywords.MENU_ID, menuId));

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

		return menus.stream().map(MenuDto::new).collect(Collectors.toList());
	}

	/**
	 * return menu by id
	 * 
	 * @param id
	 * @return menuDto by id
	 * @see com.restaurant.dto.MenuDto
	 */
	@Override
	public MenuDto getMenuById(Long menuId) {

		Menu menu = menuRepo.findById(menuId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.MENU, Keywords.MENU_ID, menuId));

		return new MenuDto(menu);
	}

}
