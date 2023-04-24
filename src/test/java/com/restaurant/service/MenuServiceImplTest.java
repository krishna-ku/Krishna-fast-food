package com.restaurant.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

		when(menuServiceImpl.createMenu(menuDTO)).thenReturn(menuDTO);

		MenuDTO returnMenuDTO = menuServiceImpl.createMenu(menuDTO);

		assertEquals(menuDTO.getName(), returnMenuDTO.getName());

	}

	/**
	 * Test Update Menu Method of Menu Service Imple Class
	 */
	@Test
	public void testUpdateMenu() {

		MenuDTO menuDTO = new MenuDTO();

		menuDTO.setName("keshav");

		when(menuServiceImpl.updateMenu(menuDTO, 1L)).thenReturn(menuDTO);

		MenuDTO returnMenuDTO = menuServiceImpl.updateMenu(menuDTO, 1L);

		assertEquals(menuDTO.getName(), returnMenuDTO.getName());

	}

}
