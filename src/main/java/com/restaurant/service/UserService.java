package com.restaurant.service;

import java.util.List;

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
	List<UserDTO> getAllUsers();

	// make user active
	String activateUser(long userId);
	
	//filter users
	List<UserDTO> filterUsers(UserDTO userDTO);
}
