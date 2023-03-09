package com.restaurant.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.dto.ApiResponse;
import com.restaurant.dto.PagingDTO;
import com.restaurant.dto.UserDTO;
import com.restaurant.service.UserService;

@RestController
@RequestMapping("/users")
//@CrossOrigin(origins = "http://localhost:4200/",maxAge = 3600)
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Add User service url : /users method : Post
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
	 * Update User by id service url: /users/id method : PUT
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
	 * Delete single and multiple User by them ids Method : DELETE Service url: /users
	 * 
	 * @param its accept long arrays
	 * @return string
	 */
//	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
//	@DeleteMapping("/{userId}")
//	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
//		this.userService.deleteUser(userId);
//		return new ResponseEntity<>(new ApiResponse("User delete successfully", true), HttpStatus.OK);
//	}
	@DeleteMapping
	public ResponseEntity<ApiResponse> deleteMultipleUsers(@RequestBody List<Long> userList){
		userService.deleteMultipleUsers(userList);
		return new ResponseEntity<>(new ApiResponse("Users Deleted Successfully",true),HttpStatus.OK);
		
	}

	/**
	 * get list of all User and filter user by the help of email Service url: /users
	 * or /users?email=email method : GET
	 * 
	 * @return list of User {@link com.restaurant.entity.User}
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping
	public ResponseEntity<PagingDTO> getAllUsers(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		PagingDTO users = userService.getAllPagedUsers(pageNumber, pageSize);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
//	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
//	@GetMapping
//	public ResponseEntity<List<UserDTO>> getAllUsers() {
//		List<UserDTO> users = userService.getAllPagedUsers();
//		return new ResponseEntity<>(users,HttpStatus.OK);
//	}


	/**
	 * filter users on the basis of id,firstName,email,deleted Service url:
	 * /users/filter method :GET
	 * 
	 * @param userDTO
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER')")
	@PostMapping("/filter")
	public ResponseEntity<List<UserDTO>> filterUsers(@RequestBody UserDTO userDTO,
			@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		String userName = (String) authentication.getPrincipal();
		List<UserDTO> filterusers = userService.filterUsers(userDTO, userName, authorities);
		return new ResponseEntity<>(filterusers, HttpStatus.OK);
	}

	/**
	 * Activate User Service url: users/activate/{userId} method :PUT
	 * 
	 * @param userId
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@PutMapping("/activate/{userId}")
	public ResponseEntity<String> activateUserEntity(@PathVariable long userId) {
		return ResponseEntity.ok(userService.activateUser(userId));
	}

	/**
	 * Upload image in database Service url: users/uploadimage/{userId} method :POST
	 * 
	 * @param image
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/uploadimage/{userId}")
	public ResponseEntity<UserDTO> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable long userId)
			throws IOException {
		UserDTO uploadImage = userService.uploadImage(image, userId);
		return new ResponseEntity<>(uploadImage, HttpStatus.OK);
	}

	/**
	 * download or view image Service url: users/downloadimage/{imageName} method
	 * :GET
	 * 
	 * @param imageName
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/viewimage/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws IOException {

		userService.viewImage(imageName, response);
	}
	/**
	 * check email is exists in database or not
	 * @param email
	 * @return
	 */
	@PostMapping("/email")
	public ResponseEntity<Boolean> checkEmailExist(@RequestBody String email){
		return ResponseEntity.ok(userService.checkEmailExist(email));
	}
	/**
	 * get logged in user details
	 * @param email
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER')")
	@GetMapping("/profile")
	public ResponseEntity<UserDTO> getLoggedInUser(@AuthenticationPrincipal String email){
		
		UserDTO loggedInUser = userService.getLoggedInUser(email);
		
		return new ResponseEntity<>(loggedInUser,HttpStatus.OK);
	}
}
