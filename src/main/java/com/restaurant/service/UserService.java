package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.UserDto;

public interface UserService {
	
	//create user
	UserDto createUser(UserDto user);
	
	//update user
	UserDto updateUser(UserDto userDto,Long id);
	
	//delete user
	void deleteUser(long id);
	
	//get users
	List<UserDto> getAllUsers();
	
	//get user
	UserDto getUserById(Long id);
}
