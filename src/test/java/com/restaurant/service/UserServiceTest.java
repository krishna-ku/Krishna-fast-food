package com.restaurant.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.context.SpringBootTest;

import com.restaurant.dto.PagingDTO;
import com.restaurant.dto.UserDTO;
import com.restaurant.entity.User;
import com.restaurant.exception.BadRequestException;
import com.restaurant.repository.UserRepo;
import com.restaurant.service.impl.UserServiceImpl;

@SpringBootTest
public class UserServiceTest {

	@Mock
	private UserServiceImpl userServiceImpl;

	@Mock
	private UserRepo userRepo;

//	@Rule
//	public ExpectedException expectedException = ExpectedException.none();

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
	 * test Create User Method of UserServiceImpl class by invalid email
	 * 
	 */
	@Test
	public void testCreateUserMethodWithInvalidEmail() {
		UserDTO userDTO = getDummyUserDTO();
		userDTO.setEmail("123");

		Mockito.when(userServiceImpl.createUser(userDTO)).thenThrow(BadRequestException.class);

		assertThrows(BadRequestException.class, () -> userServiceImpl.createUser(userDTO));
	}

	/**
	 * test Create User Method of UserServiceImpl class by email already exist
	 */
	@Test
	public void testCreateUserWithEmailAlreadyExist() {

		UserDTO userDTO = getDummyUserDTO();

		Mockito.when(userServiceImpl.createUser(userDTO)).thenThrow(BadRequestException.class);

		assertThrows(BadRequestException.class, () -> userServiceImpl.createUser(userDTO));
	}

	/**
	 * test get all users method of userServiceImpl
	 */
	@Test
	public void testGetAllUsersMethod() {

		PagingDTO<UserDTO> allPagedUsers = userServiceImpl.getAllPagedUsers(0, 10);

		OngoingStubbing<PagingDTO<UserDTO>> thenReturn = Mockito.when(userServiceImpl.getAllPagedUsers(0, 10))
				.thenReturn(allPagedUsers);

		assertThat(thenReturn).isNotNull();

	}

	@Test
	void testActivateUser() {
	    // Create a list of user ids to activate
	    List<Long> userIds = new ArrayList<>();
	    userIds.add(1L);
	    userIds.add(2L);

	    doNothing().when(userRepo).activateUsersById(userIds);

	    userServiceImpl.activateUser(userIds);

//	    verify(userRepo).activateUsersById(userIds);
	}
	/**
	 * test get logged in user method
	 */
	@Test
	public void testGetLoggedInUserMethod() {
		
		OngoingStubbing<UserDTO> thenReturn = Mockito.when(userServiceImpl.getLoggedInUser("sharmakeshav987@gmail.com")).thenReturn(getDummyUserDTO());
		
		assertThat(thenReturn).isNotNull();
		
//		assertEquals(getDummyUserDTO().getEmail(), "sharmakeshav987@gmail.com");
		
//		assertEquals(userDTO, getDummyUserDTO());
		
	}



}
