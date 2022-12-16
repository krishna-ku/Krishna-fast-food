package com.restaurant.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.restaurant.dto.UserDTO;

public interface UserService {

	// create user
	UserDTO createUser(UserDTO user);

	// update user
	UserDTO updateUser(UserDTO userDto, Long userId);

	// delete user
	void deleteUser(long userId);

	// get users
//	@Query("Select u from User u where u.email=:email")//here i am using jpql u is our UserDto alias u and e is variable and by the help of @param e is bind 
	List<UserDTO> getAllUsers(String email,String firstName);

	// get user
	UserDTO getUserById(Long userId);

	// get users by header
	List<UserDTO> findAllFilter(boolean isDeleted);
	
	//make user active
	String activateUser(long userId);
}
