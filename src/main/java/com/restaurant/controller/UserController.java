package com.restaurant.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.ApiResponse;
import com.restaurant.dto.UserDto;
import com.restaurant.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Add User service url : /user method : Post
	 * 
	 * @param UserDto
	 * @return UserDto
	 * @see com.restaurant.dto.UserDto
	 */
	@PostMapping
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto newUser = this.userService.createUser(userDto);
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}

	/**
	 * Update User by id service url: /user/id method : PUT
	 * 
	 * @param id
	 * @param UserDto
	 * @return Updated UserDto {@link com.restaurant.dto.UserDto}
	 */
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable long userId) {
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	/**
	 * Delete User by id Method : DELETE Service url: /user/id
	 * 
	 * @param id
	 * 
	 */
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
		this.userService.deleteUser(userId);
		return new ResponseEntity<>(new ApiResponse("User delete successfully", true), HttpStatus.OK);
	}

	/**
	 * get list of User Service url: /user method : GET
	 * 
	 * @return list of User {@link com.restaurant.entity.User}
	 */
	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUsers() {
		List<UserDto> users = userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	/**
	 * get detail of User by id Service url: /user/id method: GET
	 * 
	 * @param id
	 * @return UserDto
	 * @see com.restaurant.dto.UserDto
	 */
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable long userId) {

		return ResponseEntity.ok(userService.getUserById(userId));

	}

	/**
	 * get details of user isDelted or notDeleted service url :/user/
	 * 
	 * @param isDeleted=true or false
	 * @return list of users
	 */
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> findAll(
			@RequestParam(value = "isDeleted", required = false, defaultValue = "false") boolean isDeleted) {
		List<UserDto> users = userService.findAllFilter(isDeleted);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

}
