package com.restaurant.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)//padhna hai
public class UserDto {

	private long id;

	@Pattern(regexp = "[A-Za-z]{3,10}",message = "firstName must be minimum 3 to 10 alphabets !!")
	private String firstName;
	
	@Pattern(regexp = "[A-Za-z]{0,10}",message = "LastName should be alphabets and minimum 10 !!")
//	@Size(min = 0,max = 10)
	private String lastName;

	private String email;
	
	@Pattern(regexp = "[A-Za-z]+[!@#$%^&*]+[0-9]*$",message = "Password should be like this user@123")
	@Size(min = 8, max = 16,message = "password must be between 8 to 16 characters")
	private String password;
	
	
//	public UserDto(User user) {
//		this.firstName=user.getFirstName();
//		this.lastName=user.getLastName();
//		this.email=user.getEmail();
//		this.id=user.getId();
//	}
}
