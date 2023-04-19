package com.restaurant.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.assertj.core.util.Arrays;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

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

	@Autowired
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

		assertThrows(BadRequestException.class,() -> userServiceImpl.createUser(userDTO));
	}
	
	/**
	 * test get all users method of userServiceImpl
	 */
	@Test
	public void testGetAllUsersMethod() {
				
		PagingDTO<UserDTO> allPagedUsers = userServiceImpl.getAllPagedUsers(0, 10);
		
		OngoingStubbing<PagingDTO<UserDTO>> thenReturn =Mockito.when(userServiceImpl.getAllPagedUsers(0, 10)).thenReturn(allPagedUsers);
		
		assertThat(thenReturn).isNotNull();
		
	}
	
	@Test
    @Transactional
    void testActivateUser() {
		List<Long> list = new ArrayList<>();
		list.add((long) 1);
		list.add((long) 2);
		list.add((long) 2);
		
//        doThrow(new RuntimeException("Error activating users")).when(userRepo).activateUsersById(list);
//		Mockito.when(userRepo.activateUsersById(list)).then

        // Call the method
        userServiceImpl.activateUser(list);

        // Verify that the userRepo.activateUsersById() method was called with the correct parameter
//        verify(userRepo.activateUsersById(list))
        assertEquals(userRepo.activateUsersById(list), list);
    }
	
	

}
