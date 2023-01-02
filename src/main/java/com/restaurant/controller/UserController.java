package com.restaurant.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.ApiResponse;
import com.restaurant.dto.UserDTO;
import com.restaurant.entity.User;
import com.restaurant.repository.UserRepo;
import com.restaurant.service.UserService;
import com.restaurant.specification.MenuSpecification;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepo userRepo;

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
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
		this.userService.deleteUser(userId);
		return new ResponseEntity<>(new ApiResponse("User delete successfully", true), HttpStatus.OK);
	}

	/**
	 * get list of all User and filter user by the help of email Service url: /user or /user?email=email method : GET
	 * 
	 * @return list of User {@link com.restaurant.entity.User}
	 */
	@Secured("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers(UserDTO userDTO) {
		List<UserDTO> users = userService.getAllUsers(userDTO.getEmail(),userDTO.getFirstName());
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	/**
	 * get detail of User by id Service url: /user/id method: GET
	 * 
	 * @param id
	 * @return UserDto
	 * @see com.restaurant.dto.UserDTO
	 */
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable long userId) {

		return ResponseEntity.ok(userService.getUserById(userId));

	}

	/**
	 * get details of user isDelted or notDeleted service url :/user/
	 * 
	 * @param isDeleted=true or false
	 * @return list of users
	 */
	@GetMapping("/filterusers")
	public ResponseEntity<List<UserDTO>> findAll(
			@RequestParam(defaultValue = "false") boolean isDeleted) {
		List<UserDTO> users = userService.findAllFilter(isDeleted);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	/**
	 * Activate User
	 * @param userId
	 * @return
	 */
	@PutMapping("/activate/{userId}")
	public ResponseEntity<String> activateUserEntity(@PathVariable long userId){
	 	return ResponseEntity.ok(userService.activateUser(userId));
	}
	
//	@GetMapping("/deleted/{deleted}")
//	public List<User> findUsersIsDeletedOrNot(@PathVariable boolean deleted){
//		Specification<User> specification=Specification.where(MenuSpecification.userIsDeletedOrNot(deleted));
//		return userRepo.findAll(specification);
//	}

}
