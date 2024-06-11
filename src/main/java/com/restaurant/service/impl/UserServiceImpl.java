package com.restaurant.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.dto.Keywords;
import com.restaurant.dto.PagingDTO;
import com.restaurant.dto.UserDTO;
import com.restaurant.entity.User;
import com.restaurant.exception.BadRequestException;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.repository.UserRepo;
import com.restaurant.service.ImageService;
import com.restaurant.service.UserService;
import com.restaurant.specification.UserSpecification;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ImageService imageService;

	@Autowired
	EmailService emailService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Value("${project.image}")
	private String path;

	/**
	 * add User.
	 * 
	 * @param UserDTO objects
	 * @return UserDto object
	 * @see com.restaurant.dto.UserDTO
	 */
	@Transactional
	@Override
	public UserDTO createUser(UserDTO userDto) {

		log.info("Creating User for {} ", userDto);

		if (!Keywords.EMAIL_REGEX.matcher(userDto.getEmail()).matches())
			throw new BadRequestException("Invalid email format please follow this format user@gmail.com");

		User user = userRepo.findByEmail(userDto.getEmail());

		if (user == null) {
			User newUser = new User(userDto);
			if (newUser.getRole() == null)
				newUser.setRole("USER");

			newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

			User save = userRepo.save(newUser);

			CompletableFuture.runAsync(() -> {
				emailService.sendAccountCreatedMailToUser("Account Created", userDto.getEmail(),
						userDto.getFirstName());
			});

			log.info("User {} created successfully", newUser.getFirstName(), newUser.getRole());

			return new UserDTO(save);

		} else {
			throw new BadRequestException("Email already exist !!");
		}
	}

	/**
	 * update User.
	 * 
	 * @param userDto
	 * @param userId
	 * @return updated UserDto
	 * @see com.restaurant.dto.UserDTO
	 */
	public UserDTO updateUser(UserDTO userDto, Long userId) {

		log.info("Updating user for {}", userDto);

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.USER, Keywords.USER_ID, userId));

		if (!StringUtils.isEmpty(userDto.getFirstName())) {
			user.setFirstName(userDto.getFirstName());
		}

		if (!StringUtils.isEmpty(userDto.getLastName())) {
			user.setLastName(userDto.getLastName());
		}

//		BeanUtils.copyProperties(userDto, user);

		if (!StringUtils.isEmpty(userDto.getEmail())) {
			if (!Keywords.EMAIL_REGEX.matcher(userDto.getEmail()).matches()) {
				throw new BadRequestException("Invalid email format please follow this format user@gmail.com");
			}
			user.setEmail(userDto.getEmail());
		}

		if (!StringUtils.isEmpty(userDto.getPassword())) {
			user.setPassword(userDto.getPassword());
		}

		log.info("User updated successfully");

		return new UserDTO(userRepo.saveAndFlush(user));
	}

//	/**
//	 * delete User
//	 * 
//	 * @param userId
//	 * @return void
//	 */
//	@Override
//	public void deleteUser(long userId) {
//		log.info("Deleting user for {}", userId);
//		try {
//
//			userRepo.deleteById(userId);
//			log.info("User Deleted Successfully");
//
//		} catch (Exception e) {
//			throw new ResourceNotFoundException(Keywords.USER, Keywords.USER_ID, userId);
//		}
//	}

	/**
	 * Delete Multiple users by them Id
	 * 
	 * @param Arrays of usersId
	 * @return void
	 */
	@Transactional
	public void deleteMultipleUsers(List<Long> usersList) {

//		for(long userId : usersList) {
		log.info("Deleting User for {}");
		try {
			userRepo.deleteUserById(usersList);
			;
			log.info("User deleted Successfully");
		} catch (Exception e) {
			log.error(e.getMessage());
//				throw new ResourceNotFoundException(Keywords.USER, Keywords.USER_ID, usersList);
		}
//		}

	}

	/**
	 * return all Users
	 * 
	 * @return list of Users
	 * @see com.restaurant.entity.User
	 */
	@Override
//	public List<UserDTO> getAllPagedUsers() {
////		Pageable pageable = PageRequest.of(pageNumber, pageSize);
////		Page<User> page = userRepo.findAllNotDeletedUsers(pageable);
////		List<User> users = page.getContent();
//		List<User> users = userRepo.findAll();
//		List<UserDTO> collect = users.stream().map(m->new UserDTO(m)).collect(Collectors.toList());
//		return collect;
//	}
	public PagingDTO<UserDTO> getAllPagedUsers(Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = userRepo.findAllNotDeletedUsers(pageable);
		List<User> users = page.getContent();
		List<UserDTO> userDTOs = users.stream().map(m -> new UserDTO(m)).collect(Collectors.toList());
		return new PagingDTO<>(userDTOs, page.getTotalElements(), page.getTotalPages());
	}

	/**
	 * filter users on the basis of id,firstName,email,deleted
	 * 
	 * @param menuDTO
	 * @return
	 */
	@Override
	public Page<UserDTO> filterUsers(UserDTO userDTO, String userName,
			Collection<? extends GrantedAuthority> authorities, int page, int pageSize) {
		Specification<User> specification = Specification
				.where(UserSpecification.filterUsers(userDTO, userName, authorities));
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<User> users = userRepo.findAll(specification, pageable);
		List<UserDTO> userDTOs = users.getContent().stream().map(u -> new UserDTO(u)).collect(Collectors.toList());
		return new PageImpl<>(userDTOs, pageable, users.getTotalElements());
	}

	/**
	 * activate the deleted user
	 * 
	 * @param userId
	 * @return String
	 * @see com.restaurant.entity.User
	 */
	@Override
	@Transactional
	public void activateUser(List<Long> userIds) {

		log.info("Activate users for {}");
		try {
			userRepo.activateUsersById(userIds);
			log.info("Activate users successfully {}");

		} catch (Exception e) {
			log.error(e.getMessage());
		}

//		long userId=userIds.get(0);
//		User user = userRepo.findById(userId)
//				.orElseThrow(() -> new ResourceNotFoundException(Keywords.USER, Keywords.USER_ID, userId));
//		user.setDeleted(false);
//		userRepo.save(user);
//		return "User is active";
	}

	/**
	 * Upload Image in DB
	 * 
	 * @param image
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	@Override
	public UserDTO uploadImage(MultipartFile image, long userId) throws IOException {
		log.info("Uploading image in user profile");

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.USER, Keywords.USER_ID, userId));
		String imageName = this.imageService.uploadImage(path, image);
		user.setImageName(imageName);
		userRepo.save(user);

		log.info("upload image successfully");
		return new UserDTO(user);
	}

	/**
	 * view image or view image
	 * 
	 * @param image
	 * @param response
	 * @throws IOException
	 */
	@Override
	public void viewImage(String image, HttpServletResponse response) throws IOException {

		InputStream resource = this.imageService.getResource(path, image);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

	/**
	 * check email is exists in database or not
	 * 
	 * @param email
	 * @return
	 */
	public boolean checkEmailExist(String email) {
		User user = userRepo.findByEmail(email);
		if (user != null)
			return true;
		else {
			return false;
		}
	}

	/**
	 * get logged in user
	 * 
	 * @param email
	 * @return UserDTO
	 */
	@Override
	public UserDTO getLoggedInUser(String email) {

		log.info("getting user details {}");

		User user = userRepo.findByEmail(email);

		log.info("get user successfully");

		return new UserDTO(user);
	}
}
