package com.restaurant.service;

import com.restaurant.dto.UserDTO;
import com.restaurant.entity.User;
import com.restaurant.exception.BadRequestException;
import com.restaurant.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.constraints.Null;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;


@SpringBootTest

public class UserServiceTest {


    private UserDTO getDummyUserDTO() {

        return new UserDTO("keshav", "sharma", "USER", "sharmakeshav987@gmail.com", "Delhi", "", "9876543210", false, "Keshav@123");
    }

    private User getDummyUser() {

        UserDTO userDTO = getDummyUserDTO();
        User user = new User(userDTO);
        return user;
    }

    @Test
    public void testCreateUserMethodWithInvalidEmail() {
        UserService userService = mock(UserServiceImpl.class);
        UserDTO userDTO = getDummyUserDTO();
        userDTO.setEmail("123");

        Mockito.when(userService.createUser(userDTO)).thenThrow(BadRequestException.class);

        assertThrows(BadRequestException.class, () -> userService.createUser(userDTO));

    }

//	 public void testCreateUserMethod() {
//
//
//
//	 }

}
