package com.restaurant.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.dto.Keywords;
import com.restaurant.dto.MenuDTO;
import com.restaurant.dto.UserDTO;
import com.restaurant.entity.Menu;
import com.restaurant.entity.User;
import com.restaurant.exception.BadRequestException;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.repository.MenuRepo;
import com.restaurant.service.MenuService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepo menuRepo;
	
	@Autowired
	private EntityManager entityManager;

	/**
	 * add Menu.
	 * 
	 * @param menuDto
	 * @return MenuDto
	 * @see com.restaurant.dto.MenuDTO
	 */
	@Override
	public MenuDTO createMenu(MenuDTO menuDto) {
		
		log.info("Creating menu for {} ",menuDto);

		Menu menuExist = this.menuRepo.findByName(menuDto.getName());

		if (menuExist == null) {
			Menu newMenu = new Menu(menuDto);
			
			log.info("Menu created successfully");
			
			return new MenuDTO(menuRepo.save(newMenu));
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
	 * @see com.restaurant.dto.MenuDTO
	 */
	@Override
	public MenuDTO updateMenu(MenuDTO menuDto, Long menuId) {
		
		log.info("Updating menu for {} ",menuId);

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

		log.info("Menu updated successfully");
		
		return new MenuDTO(menuRepo.save(updatedMenu));
	}

	/**
	 * delete Menu
	 * 
	 * @param id
	 * @return void
	 */

	@Override
	public void deleteMenu(long menuId) {
		
		log.info("Deleting menu for {} ",menuId);

		Menu menu = this.menuRepo.findById(menuId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.MENU, Keywords.MENU_ID, menuId));

		menuRepo.delete(menu);
		log.info("Menu deleted successfully");
	}

	/**
	 * return all menus
	 * 
	 * @return list of menuDtos
	 * @see com.restaurant.dto.MenuDTO
	 */
	@Override
	public List<MenuDTO> getAllMenus() {

		List<Menu> menus = menuRepo.findAll();

//		List<MenuDto> menuDtos = menus.stream().map(menu -> menuToDto(menu)).collect(Collectors.toList());

		return menus.stream().map(MenuDTO::new).collect(Collectors.toList());
	}
	
	/**
	 * return menus by filter
	 * 
	 * @return list of menuDtos
	 * @see com.restaurant.dto.MenuDTO
	 */
	@Override
	public List<MenuDTO> menusByFilter(float price) {

		List<Menu> menus = menuRepo.menuItemsByFilter(price);
		
		return menus.stream().map(MenuDTO::new).collect(Collectors.toList());
	}

	/**
	 * return menu by id
	 * 
	 * @param id
	 * @return menuDto by id
	 * @see com.restaurant.dto.MenuDTO
	 */
	@Override
	public MenuDTO getMenuById(Long menuId) {

		Menu menu = menuRepo.findById(menuId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.MENU, Keywords.MENU_ID, menuId));

		return new MenuDTO(menu);
	}
	
	/**
	 * return lists of deleted and undeleted menus
	 * 
	 * @param isDeleted=true or false
	 * @return list of deleted or undeleted menus
	 * @see com.restaurant.entity.MenuDtos
	 */
	public List<MenuDTO> findAllFilter(boolean isDeleted) {
		Session session = entityManager.unwrap(Session.class);
		Filter filter = session.enableFilter("deletedMenuFilter");
		filter.setParameter("isDeleted", isDeleted);
		List<Menu> menus = menuRepo.findAll();
		session.disableFilter("deletedMenuFilter");

		return menus.stream().map(MenuDTO::new).collect(Collectors.toList());
	}
	
	

}
