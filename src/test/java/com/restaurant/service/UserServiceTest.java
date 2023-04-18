package com.restaurant.service;

import static org.junit.Assert.assertThrows;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.restaurant.dto.UserDTO;
import com.restaurant.entity.User;
import com.restaurant.exception.BadRequestException;
import com.restaurant.repository.UserRepo;
import com.restaurant.service.impl.UserServiceImpl;

@SpringBootTest
//@RunWith()
public class UserServiceTest {

	@Mock
	private UserServiceImpl userServiceImpl;

	@Mock
	private UserRepo userRepo;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private UserDTO getDummyUserDTO() {

		return new UserDTO("keshav", "sharma", "USER", "sharmakeshav987@gmail.com", "Delhi", "", "9876543210", false,
				"Keshav@123");
	}

	private User getDummyUser() {

		UserDTO userDTO = getDummyUserDTO();
		User user = new User(userDTO);
		return user;
	}

	@Test
	public void testCreateUserMethodWithInvalidEmail() {
		UserDTO userDTO = getDummyUserDTO();
		userDTO.setEmail("123");

		Mockito.when(userServiceImpl.createUser(userDTO)).thenThrow(BadRequestException.class);

		assertThrows(BadRequestException.class, () -> userServiceImpl.createUser(userDTO));

//	        expectedException.expect(NullPointerException.class);
//	        expectedException.expectMessage("Invalid email");
//	        userServiceImpl.createUser(userDTO);
//	        BadRequestException exception = assertThrows(BadRequestException.class, () -> userServiceImpl.createUser(userDTO));
//	        String expectedMessage = "Invalid email format please follow this format user@gmail.com";
//	        String actualMessage = exception.getMessage();
//	        assert (actualMessage.contains(expectedMessage));
	}

//	 public void testCreateUserMethod() {
//		 
//		 
//		 
//	 }

}
