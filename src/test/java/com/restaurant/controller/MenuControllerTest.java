package com.restaurant.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;

import com.restaurant.dto.MenuDTO;
import com.restaurant.service.MenuService;

//@ExtendWith(MockitoExtension.class)
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(MenuController.class)
@SpringBootTest
//@ComponentScan("com.restaurant")
public class MenuControllerTest {

	@Mock
	private MenuService menuService;

//	@InjectMocks
//	private MenuController menuController;

//	@Autowired
//	private MockMvc mockMvc;
//	 = MockMvcBuilders.standaloneSetup(new MenuController(menuService)).build()

//	@Before
//	public void setup() {
//		mockMvc = MockMvcBuilders.standaloneSetup(new MenuController(menuService)).build();
//		String string="test";
//	}

	@Test
	void testCreateMenu() throws Exception {

		String requestBody = "{\r\n" + "    \"name\": \"ketchup\",\r\n" + "    \"price\": 1,\r\n"
				+ "    \"description\": \"Add extra ketchup for extra members\",\r\n" + "    \"type\":\"addon\",\r\n"
				+ "    \"category\":{\r\n" + "        \"id\":3\r\n" + "    }\r\n" + "}";

		MenuDTO menuDto = new MenuDTO();
		menuDto.setName("pizza");
		menuDto.setDescription("delicious");
		menuDto.setPrice(100F);
		menuDto.setType("Normal");

		MenuDTO createdMenu = new MenuDTO();
		createdMenu.setName("pizza");
		createdMenu.setDescription("delicious");
		createdMenu.setPrice(100F);

//		Mockito.when(menuService.createMenu(menuDto)).thenReturn(createdMenu);

//		mockMvc.perform(post("/menus").contentType(MediaType.APPLICATION_JSON).content(requestBody))
//				.andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("Test Menu"))
//				.andExpect(jsonPath("$.description").value("Test description"))
//				.andExpect(jsonPath("$.price").value(9.99));
	}
}