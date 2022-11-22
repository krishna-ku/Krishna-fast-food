package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.UserDto;

public interface UserService {
	
	//create user
	UserDto createUser(UserDto user);
	
	//update user
	UserDto updateUser(UserDto userDto,Long userId);
	
	//delete user
	void deleteUser(long userId);
	
	//get users
	List<UserDto> getAllUsers();
	
	//get user
	UserDto getUserById(Long userId);
}
