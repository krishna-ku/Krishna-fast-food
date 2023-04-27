package com.restaurant.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.restaurant.dto.UserDTO;
import com.restaurant.entity.User;
import com.restaurant.service.impl.UserServiceImpl;

@SpringBootTest
public class UserControllerTest {

	@Mock
	private UserServiceImpl userServiceImpl;

	@InjectMocks
	private UserController userController;

	/**
	 * get dummy object of userDTO for testing
	 * 
	 * @return UserDTO
	 */
	private UserDTO getDummyUserDTO() {

		return new UserDTO("keshav", "sharma", "USER", "sharmakeshav987@gmail.com", "Delhi", "", "9876543210", false,
				"Keshav@123");
	}

	/**
	 * get dummy object of user for testing
	 * 
	 * @return User
	 */
	private User getDummyUser() {

		UserDTO userDTO = getDummyUserDTO();
		User user = new User(userDTO);
		return user;
	}

	/**
	 * test user controller create user method
	 */
	@Test
	public void testCreateUser_AndReturnStatus201() {

		UserDTO userDTO = getDummyUserDTO();

		ResponseEntity<UserDTO> expectResponse = new ResponseEntity<>(getDummyUserDTO(), HttpStatus.CREATED);

		Mockito.when(userServiceImpl.createUser(userDTO)).thenReturn(userDTO);

		ResponseEntity<UserDTO> actualResponse = userController.createUser(userDTO);

		Assertions.assertEquals(expectResponse, actualResponse);

	}

	/**
	 * test update user method of User Controller
	 */
	@Test
	public void testUpdateUser_AndReturnStatus200() {
		long userId = 1;
		UserDTO userDto = getDummyUserDTO();

		ResponseEntity<UserDTO> expectedResponse = new ResponseEntity<>(userDto, HttpStatus.OK);

		Mockito.when(userServiceImpl.updateUser(userDto, userId)).thenReturn(userDto);

		ResponseEntity<UserDTO> actualResponse = userController.updateUser(userDto, userId);

		Assertions.assertEquals(expectedResponse, actualResponse);
	}

}
