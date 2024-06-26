package com.restaurant.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.dto.PagingDTO;
import com.restaurant.dto.UserDTO;

public interface UserService {

	// create user
	UserDTO createUser(UserDTO user);

	// update user
	UserDTO updateUser(UserDTO userDto, Long userId);

	// delete user
//	void deleteUser(long userId);

	// get users
//	@Query("Select u from User u where u.email=:email")//here i am using jpql u is our UserDto alias u and e is variable and by the help of @param e is bind 
	PagingDTO getAllPagedUsers(Integer pageNumber, Integer pageSize);
//	List<UserDTO> getAllPagedUsers();

	// make user active
	void activateUser(List<Long> userId);

	// filter users
	Page<UserDTO> filterUsers(UserDTO userDTO, String userName,
			Collection<? extends GrantedAuthority> authorities, int page, int pageSize);

	// upload image in user profile
	UserDTO uploadImage(MultipartFile image, long userId) throws IOException;

	// download image or view image
	void viewImage(String image, HttpServletResponse response) throws IOException;
	
	//check email is exists or not
	boolean checkEmailExist(String email);
	
	//get logged in user
	UserDTO getLoggedInUser(String email);
	
	//Delete Multiple users by them Id
	void deleteMultipleUsers(List<Long> usersList);
	
	
}
