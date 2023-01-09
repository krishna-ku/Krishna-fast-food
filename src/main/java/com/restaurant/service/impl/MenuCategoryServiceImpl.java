package com.restaurant.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.restaurant.dto.Keywords;
import com.restaurant.dto.MenuCategoryDTO;
import com.restaurant.entity.MenuCategory;
import com.restaurant.exception.BadRequestException;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.repository.MenuCategoryRepo;
import com.restaurant.service.MenuCategoryService;
import com.restaurant.specification.MenuCategorySpecification;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenuCategoryServiceImpl implements MenuCategoryService {

	@Autowired
	private MenuCategoryRepo menuCategoryRepo;

	/**
	 * Create New Menu category
	 * 
	 * @param MenuCategoryDto object
	 * @return MenuCategoryDto
	 */
	@Override
	public MenuCategoryDTO createCategory(MenuCategoryDTO menuCategoryDTO) {

		log.info("Creating New Category for {}", menuCategoryDTO);

		MenuCategoryDTO menuCategoryExists = this.menuCategoryRepo.findByName(menuCategoryDTO.getName());

		if (menuCategoryExists == null) {

			MenuCategory newMenuCategory = new MenuCategory(menuCategoryDTO);
			menuCategoryRepo.save(newMenuCategory);

			log.info("New menu category is created successfully");
			return new MenuCategoryDTO(newMenuCategory);
		} else {
			throw new BadRequestException("Menu Category is already exist");
		}
	}

	/**
	 * update MenuCategory
	 * 
	 * @param menuCategoryDTO
	 * @param id
	 * @return updated menuCategoryDTO
	 * @see com.restaurant.dto.MenuCategoryDTO
	 */
	@Override
	public MenuCategoryDTO updateCategory(MenuCategoryDTO menuCategoryDTO, long menuCategoryId) {

		log.info("Updating menu for {}", menuCategoryId);

		MenuCategory menuCategory = menuCategoryRepo.findById(menuCategoryId).orElseThrow(
				() -> new ResourceNotFoundException(Keywords.MENU_CATEGORY, Keywords.MENU_CATEGORY_ID, menuCategoryId));

		if (!StringUtils.isEmpty(menuCategoryDTO.getName()))
			menuCategory.setName(menuCategoryDTO.getName());

		log.info("Menu updated successfully");

		return new MenuCategoryDTO(menuCategoryRepo.save(menuCategory));
	}

	/**
	 * delete MenuCategory
	 * 
	 * @param menuCategoryId
	 * @return void
	 */
	@Override
	public void deleteCategory(long menuCategoryId) {

		log.info("Deleting menu for {}", menuCategoryId);

		menuCategoryRepo.findById(menuCategoryId).orElseThrow(
				() -> new ResourceNotFoundException(Keywords.MENU_CATEGORY, Keywords.MENU_CATEGORY_ID, menuCategoryId));

		menuCategoryRepo.deleteById(menuCategoryId);

		log.info("Menu category is deleted successfully");

	}

	/**
	 * return all menuCategories
	 * 
	 * @return list of menuCategoriesDTOs
	 * @see com.restaurant.dto.MenuCategoryDTO
	 */
	@Override
	public List<MenuCategoryDTO> getAllMenusCategories() {

		List<MenuCategory> menuCategories = menuCategoryRepo.findAll();

		return menuCategories.stream().map(m -> new MenuCategoryDTO(m)).collect(Collectors.toList());
	}

	/**
	 * filter menuCategories on the basis of name and deleted
	 * 
	 * @param menuCategoryDTO
	 * @return menuCategoryDTO
	 */
	@Override
	public List<MenuCategoryDTO> filterMenuCategories(MenuCategoryDTO menuCategoryDTO) {

		Specification<MenuCategory> specification = Specification
				.where(MenuCategorySpecification.filterMenuCategories(menuCategoryDTO));

		return menuCategoryRepo.findAll(specification).stream().map(m -> new MenuCategoryDTO(m))
				.collect(Collectors.toList());
	}

}
