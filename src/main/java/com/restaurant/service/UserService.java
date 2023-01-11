package com.restaurant.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

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
	List<UserDTO> getAllUsers(Integer pageNumber,Integer pageSize);

	// make user active
	String activateUser(long userId);
	
	//filter users
	List<UserDTO> filterUsers(UserDTO userDTO);
	
	//upload image in user profile
	UserDTO uploadImage(MultipartFile image, long userId) throws IOException;
	
	//download image or view image
	void viewImage(String image,HttpServletResponse response) throws IOException;
}
