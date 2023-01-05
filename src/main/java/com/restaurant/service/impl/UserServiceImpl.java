package com.restaurant.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.restaurant.dto.Keywords;
import com.restaurant.dto.UserDTO;
import com.restaurant.entity.User;
import com.restaurant.exception.BadRequestException;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.repository.UserRepo;
import com.restaurant.service.UserService;
import com.restaurant.specification.UserSpecification;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	EmailService emailService;

	@Autowired
	PasswordEncoder passwordEncoder;

//	private static final Logger LOG=LoggerFactory.getLogger(UserServiceImpl.class);

	/**
	 * add User.
	 * 
	 * @param UserDTO objects
	 * @return UserDto object
	 * @see com.restaurant.dto.UserDTO
	 */
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

			emailService.sendAccountCreatedMailToUser("Account Created", userDto.getEmail(), userDto.getFirstName());

			log.info("User created successfully");

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

		User user2 = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.USER, Keywords.USER_ID, userId));

		if (!StringUtils.isEmpty(userDto.getFirstName())) {
			user2.setFirstName(userDto.getFirstName());
		}

		if (!StringUtils.isEmpty(userDto.getLastName())) {
			user2.setLastName(userDto.getLastName());
		}

		if (!StringUtils.isEmpty(userDto.getEmail())) {
			if (!Keywords.EMAIL_REGEX.matcher(userDto.getEmail()).matches()) {
				throw new BadRequestException("Invalid email format please follow this format user@gmail.com");
			}
			user2.setEmail(userDto.getEmail());
		}

		if (!StringUtils.isEmpty(userDto.getPassword())) {
			user2.setPassword(userDto.getPassword());
		}

		log.info("User updated successfully");

		return new UserDTO(userRepo.save(user2));
	}

	/**
	 * delete User
	 * 
	 * @param userId
	 * @return void
	 */
	@Override
	public void deleteUser(long userId) {

		log.info("Deleting user for {}", userId);

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.USER, Keywords.USER_ID, userId));

		this.userRepo.delete(user);
		log.info("User Deleted Successfully");
	}

	/**
	 * return all Users
	 * 
	 * @return list of Users
	 * @see com.restaurant.entity.User
	 */
	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		return users.stream().map(user -> new UserDTO(user)).collect(Collectors.toList());
	}

	/**
	 * filter users on the basis of id,firstName,email,deleted
	 * 
	 * @param menuDTO
	 * @return
	 */
	@Override
	public List<UserDTO> filterUsers(UserDTO userDTO) {
		Specification<User> specification = Specification.where(UserSpecification.filterUsers(userDTO));
		return userRepo.findAll(specification).stream().map(user -> new UserDTO(user)).collect(Collectors.toList());
	}

	/**
	 * activate the deleted user
	 * 
	 * @param userId
	 * @return String
	 * @see com.restaurant.entity.User
	 */
	@Override
	public String activateUser(long userId) {

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.USER, Keywords.USER_ID, userId));
		user.setDeleted(false);
		userRepo.save(user);
		return "User is active";
	}

}
