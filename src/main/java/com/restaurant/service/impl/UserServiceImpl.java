package com.restaurant.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.dto.Keywords;
import com.restaurant.dto.UserDto;
import com.restaurant.entity.User;
import com.restaurant.exception.BadRequestException;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.repository.UserRepo;
import com.restaurant.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	EmailService emailService;
	
	private static final Logger LOG=LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private EntityManager entityManager;

	/**
	 * add User.
	 * 
	 * @param UserDto objects
	 * @return UserDto object
	 * @see com.restaurant.dto.UserDto
	 */
	@Override
	public UserDto createUser(UserDto userDto) {
		
		LOG.info("Creating User for {} ",userDto);

		if (!Keywords.EMAIL_REGEX.matcher(userDto.getEmail()).matches())
			throw new BadRequestException("Invalid email format please follow this format user@gmail.com");

		User user = userRepo.findByEmail(userDto.getEmail());

		if (user == null) {
			User newUser = new User(userDto);
			User save = userRepo.save(newUser);
			
			emailService.sendMailToUser("Account Created",userDto.getEmail(),userDto.getFirstName());
			
			LOG.info("User created successfully");
						
			return new UserDto(save);

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
	 * @see com.restaurant.dto.UserDto
	 */
	public UserDto updateUser(UserDto userDto, Long userId) {

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

		return new UserDto(userRepo.save(user2));
	}

	/**
	 * delete User
	 * 
	 * @param userId
	 * @return void
	 */
	@Override
	public void deleteUser(long userId) {

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.USER, Keywords.USER_ID, userId));

		this.userRepo.delete(user);
	}

	/**
	 * return all Users
	 * 
	 * @return list of Users
	 * @see com.restaurant.entity.User
	 */
	@Override
	public List<UserDto> getAllUsers(String email, String firstName) {
		List<User> users = this.userRepo.findUsersByEmail(email, firstName);

		return users.stream().map(user -> new UserDto(user)).collect(Collectors.toList());
	}

	/**
	 * find User by id
	 * 
	 * @param userId
	 * @return User by id
	 * @see com.restaurant.entity.User
	 */
	@Override
	public UserDto getUserById(Long userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.USER, Keywords.USER_ID, userId));

		return new UserDto(user);
	}

	/**
	 * return lists of deleted and undeleted users
	 * 
	 * @param isDeleted=true or false
	 * @return list of deleted or undeleted User
	 * @see com.restaurant.entity.User
	 */
	public List<UserDto> findAllFilter(boolean isDeleted) {
		Session session = entityManager.unwrap(Session.class);
		Filter filter = session.enableFilter("deletedUserFilter");
		filter.setParameter("isDeleted", isDeleted);
		List<User> users = userRepo.findAll();
		session.disableFilter("deletedUserFilter");

		return users.stream().map(u -> new UserDto(u)).collect(Collectors.toList());
	}

	public String activateUser(long userId) {

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.USER, Keywords.USER_ID, userId));
		user.setDeleted(false);
		userRepo.save(user);
		return "User is active";
	}

}
