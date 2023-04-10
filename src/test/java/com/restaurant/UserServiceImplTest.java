package com.restaurant;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.restaurant.dto.UserDTO;
import com.restaurant.exception.BadRequestException;
import com.restaurant.repository.UserRepo;
import com.restaurant.service.ImageService;
import com.restaurant.service.impl.UserServiceImpl;

@SpringBootTest
public class UserServiceImplTest {

	@Mock
	private UserRepo userRepo;

	@Mock
	private ImageService imageService;

	@InjectMocks
	private UserServiceImpl userService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

//	@Test
//	@DisplayName("Create User Test")
//	void testCreateUser() {
//		// given
//		UserDTO userDto = new UserDTO();
//		userDto.setFirstName("John");
//		userDto.setLastName("Doe");
//		userDto.setEmail("john.doe@gmail.com");
//		userDto.setPassword("password");
//
//		User existingUser = new User();
//		existingUser.setEmail(userDto.getEmail());
//
//		// when
//		Mockito.when(userRepo.findByEmail(userDto.getEmail())).thenReturn(existingUser);
//		assertThatExceptionOfType(BadRequestException.class).isThrownBy(() -> userService.createUser(userDto))
//				.withMessage("Email already exist !!");
//
//		Mockito.when(userRepo.findByEmail(userDto.getEmail())).thenReturn(null);
//		Mockito.when(userRepo.save(Mockito.any(User.class))).thenReturn(existingUser);
//
//		UserDTO actualUserDto = userService.createUser(userDto);
//
//		// then
//		assertThat(actualUserDto).isNotNull();
//		assertThat(actualUserDto.getFirstName()).isEqualTo(userDto.getFirstName());
//		assertThat(actualUserDto.getLastName()).isEqualTo(userDto.getLastName());
//		assertThat(actualUserDto.getEmail()).isEqualTo(userDto.getEmail());
//	}

	@Test
	@DisplayName("Create User With Invalid Email Test")
	void testCreateUserWithInvalidEmail() {
		// given
		UserDTO userDto = new UserDTO();
		userDto.setFirstName("John");
		userDto.setLastName("Doe");
		userDto.setEmail("invalid.email");
		userDto.setPassword("password");

		// when / then
		assertThatExceptionOfType(BadRequestException.class).isThrownBy(() -> userService.createUser(userDto))
				.withMessage("Invalid email format please follow this format user@gmail.com");
	}

//    @Test
//    @DisplayName("Update User Test")
//    void testUpdateUser() {
//        // given
//        long userId = 1L;
//
//        UserDTO userDto = new UserDTO();
//        userDto.setFirstName("Jane");
//        userDto.setLastName("Doe");
//        userDto.setEmail("jane.doe@gmail.com");
//        userDto.setPassword("newpassword");
//
//        User existingUser = new User();
//        existingUser.setId(userId);
//        existingUser.setFirstName("John");
//        existingUser.setLastName("Doe");
//        existingUser.setEmail("john.doe@gmail.com");
//        existingUser.setPassword("password");
//
//        // when
//        Mockito.when(userRepo.findById(userId
}
