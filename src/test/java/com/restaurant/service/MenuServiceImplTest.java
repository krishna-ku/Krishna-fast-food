package com.restaurant.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.restaurant.dto.MenuCategoryDTO;
import com.restaurant.dto.MenuDTO;
import com.restaurant.service.impl.MenuServiceImpl;

@SpringBootTest
public class MenuServiceImplTest {

	@Mock
	private MenuServiceImpl menuServiceImpl;

	/**
	 * Test Create Menu method of Menu Service Impl Class
	 */
	@Test
	public void testCreateMenu() {

		MenuCategoryDTO categoryDTO = new MenuCategoryDTO();
		categoryDTO.setId(1L);

		MenuDTO menuDTO = new MenuDTO();

		menuDTO.setName("Pizza");
		menuDTO.setPrice(100);
		menuDTO.setDescription("delicious");
		menuDTO.setType("Nomral");
		menuDTO.setCategory(categoryDTO);

		Mockito.when(menuServiceImpl.createMenu(menuDTO)).thenReturn(menuDTO);

		MenuDTO returnMenuDTO = menuServiceImpl.createMenu(menuDTO);

		Assertions.assertEquals(menuDTO.getName(), returnMenuDTO.getName());

	}

	/**
	 * Test Update Menu Method of Menu Service Imple Class
	 */
	@Test
	public void testUpdateMenu() {

		MenuDTO menuDTO = new MenuDTO();

		menuDTO.setName("keshav");

		Mockito.when(menuServiceImpl.updateMenu(menuDTO, 1L)).thenReturn(menuDTO);

		MenuDTO returnMenuDTO = menuServiceImpl.updateMenu(menuDTO, 1L);

		Assertions.assertEquals(menuDTO.getName(), returnMenuDTO.getName());

	}

}
