package com.restaurant.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.dto.ExcelHelper;
import com.restaurant.dto.Keywords;
import com.restaurant.dto.MenuDTO;
import com.restaurant.entity.Menu;
import com.restaurant.exception.BadRequestException;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.repository.MenuRepo;
import com.restaurant.service.MenuService;
import com.restaurant.specification.MenuSpecification;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepo menuRepo;

	/**
	 * add Menu.
	 * 
	 * @param menuDto
	 * @return MenuDto
	 * @see com.restaurant.dto.MenuDTO
	 */
	@Override
	public MenuDTO createMenu(MenuDTO menuDto) {

		log.info("Creating menu for {} ", menuDto);

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

		log.info("Updating menu for {} ", menuId);

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

		log.info("Deleting menu for {} ", menuId);

		this.menuRepo.findById(menuId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.MENU, Keywords.MENU_ID, menuId));

		menuRepo.deleteById(menuId);
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
	 * filter menus on the basis of id,price,deleted,name
	 * 
	 * @param menuDTO
	 * @return
	 */
	@Override
	public List<MenuDTO> filterMenus(MenuDTO menuDTO) {
		Specification<Menu> specification = Specification.where(MenuSpecification.menuFilters(menuDTO));
		return menuRepo.findAll(specification).stream().map(m -> new MenuDTO(m)).collect(Collectors.toList());
	}

	/**
	 * activate the deleted menu
	 * 
	 * @param menuId
	 * @return String
	 * @see com.restaurant.entity.Menu
	 */
	@Override
	public String activateMenu(long menuId) {
		Menu menu = menuRepo.findById(menuId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.MENU, Keywords.MENU_ID, menuId));
		menu.setDeleted(false);
		menuRepo.save(menu);
		return "Menu is Activated";
	}

	/**
	 * save all data which presents in excle file
	 * 
	 * @param file
	 */
	public void saveExcelFile(MultipartFile uploadMenusFromExcelfile) {
		log.info("Converting excel file into list for{}", uploadMenusFromExcelfile);
		try {
			Set<MenuDTO> menus = ExcelHelper.convertExcelFileToListOfMenus(uploadMenusFromExcelfile.getInputStream());
			List<Menu> menuList = menus.stream().map(o -> {
				Menu menu = menuRepo.findByName(o.getName());
				if (menu == null) {
					menu = new Menu(o);
				} else {
					menu.setDescription(o.getDescription());
					menu.setPrice(o.getPrice());
				}
				return menu;
			}).collect(Collectors.toList());
			this.menuRepo.saveAll(menuList);
			log.info("Successfully data saved in Database");
		} catch (IOException e) {
			log.error("Error while saving data from excel file to database", e.getMessage());
		}
	}

	/**
	 * save all data which presents in csv file
	 * 
	 * @param file
	 */
	public void saveCsvFile(MultipartFile uploadMenusFromCsvFile) {
		try {

			Set<MenuDTO> menus = ExcelHelper.csvToMenus(uploadMenusFromCsvFile.getInputStream());
			List<Menu> menuList = menus.stream().map(o -> {
				Menu menu = menuRepo.findByName(o.getName());
				if (menu == null) {
					menu = new Menu(o);
				} else {
					menu.setDescription(o.getDescription());
					menu.setPrice(o.getPrice());
				}
				return menu;
			}).collect(Collectors.toList());
			menuRepo.saveAll(menuList);
		} catch (Exception e) {
			log.error("Error while saving data from csv file to database", e.getMessage());
		}
	}

	/**
	 * check file is csv or excel then save into database
	 * 
	 * @param file
	 */
	public void checkUploadFileIsCsvOrExcel(MultipartFile uploadMenuFromFile) {
		try {

			if (uploadMenuFromFile.getContentType() == null)
				throw new BadRequestException("please provide a valid CSV or Excel file");

			if (!ExcelHelper.checkExcelFormat(uploadMenuFromFile) && !ExcelHelper.checkCSVFormat(uploadMenuFromFile)) {
				log.info("file is not csv or not excel");
				throw new BadRequestException("please provide a valid CSV or Excel file");
			}

			if (ExcelHelper.checkExcelFormat(uploadMenuFromFile)) {
				saveExcelFile(uploadMenuFromFile);
				log.info("Excel file is saved successfully");
			}

			if (ExcelHelper.checkCSVFormat(uploadMenuFromFile)) {
				saveCsvFile(uploadMenuFromFile);
				log.info("CSV file is saved successfully");
			}
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

}
