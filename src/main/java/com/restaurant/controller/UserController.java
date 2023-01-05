package com.restaurant.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.ApiResponse;
import com.restaurant.dto.UserDTO;
import com.restaurant.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Add User service url : /user method : Post
	 * 
	 * @param UserDTO
	 * @return UserDto
	 * @see com.restaurant.dto.UserDTO
	 */
	@PostMapping
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDto) {
		UserDTO newUser = this.userService.createUser(userDto);
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}

	/**
	 * Update User by id service url: /user/id method : PUT
	 * 
	 * @param id
	 * @param UserDTO
	 * @return Updated UserDto {@link com.restaurant.dto.UserDTO}
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER')")
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDto, @PathVariable long userId) {
		UserDTO updatedUser = this.userService.updateUser(userDto, userId);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	/**
	 * Delete User by id Method : DELETE Service url: /user/id
	 * 
	 * @param id
	 * 
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
		this.userService.deleteUser(userId);
		return new ResponseEntity<>(new ApiResponse("User delete successfully", true), HttpStatus.OK);
	}

	/**
	 * get list of all User and filter user by the help of email Service url: /user
	 * or /user?email=email method : GET
	 * 
	 * @return list of User {@link com.restaurant.entity.User}
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserDTO> users = userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	/**
	 * filter users on the basis of id,firstName,email,deleted
	 * 
	 * @param userDTO
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping("/filter")
	public ResponseEntity<List<UserDTO>> filterUsers(@RequestBody UserDTO userDTO) {
		List<UserDTO> filterusers = userService.filterUsers(userDTO);
		return new ResponseEntity<>(filterusers, HttpStatus.OK);
	}

	/**
	 * Activate User
	 * 
	 * @param userId
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@PutMapping("/activate/{userId}")
	public ResponseEntity<String> activateUserEntity(@PathVariable long userId) {
		return ResponseEntity.ok(userService.activateUser(userId));
	}

}
