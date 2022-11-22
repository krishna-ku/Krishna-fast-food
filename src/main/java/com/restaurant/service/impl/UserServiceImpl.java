package com.restaurant.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
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

	/**
	 * add User.
	 * 
	 * @param UserDto objects
	 * @return UserDto object
	 * @see com.restaurant.dto.UserDto
	 */
	@Override
	public UserDto createUser(UserDto userDto) {

		if (!Keywords.EMAIL_REGEX.matcher(userDto.getEmail()).matches())
			throw new BadRequestException("Invalid email format please follow this format user@gmail.com");

		User user = userRepo.findByEmail(userDto.getEmail());

		if (user == null) {

			User newUser = new User(userDto);
			return new UserDto(userRepo.save(newUser));

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
	public UserDto updateUser(UserDto userDto, Long userId) {// validation here for email and id also

		User user2 = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.USER, Keywords.USER_ID, userId));

		if (!StringUtils.isEmpty(userDto.getFirstName())) {
			user2.setFirstName(userDto.getFirstName());
		}

		if (!StringUtils.isEmpty(userDto.getLastName())) {
			user2.setLastName(userDto.getLastName());
		}

		if (!StringUtils.isEmpty(userDto.getEmail())) {
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
	 * return all Users present in User
	 * 
	 * @return list of Users
	 * @see com.restaurant.entity.User
	 */
	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();

//		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return users.stream().map(user -> new UserDto(user)).collect(Collectors.toList());
//		return users.stream().map(this::userToDto).collect(Collectors.toList());
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

}
