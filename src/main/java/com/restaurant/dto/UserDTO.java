package com.restaurant.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.restaurant.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class UserDTO {

	private long id;

	@Pattern(regexp = "[A-Za-z]{1,10}", message = "firstName must be minimum 1 to 10 alphabets !!")
	private String firstName;

	@Pattern(regexp = "[A-Za-z]{0,10}", message = "LastName should be alphabets and minimum 10 !!")
	private String lastName;

	@Pattern(regexp = "(ADMIN|USER)", message = "please enter role ADMIN or USER in capital letters")
	private String role;

	private String email;

	private String address;

	private String imageName;

	private String mobileNumber;

	private Boolean deleted;

	//for fetching data from orders
	private OrderDTO orders;

	@Pattern(regexp = "[A-Za-z]+[!@#$%^&*]+[0-9]*$", message = "Password should be like this user@123")
	@Size(min = 8, max = 16, message = "password must be between 8 to 16 characters")
	private String password;

	public UserDTO(User user) {
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.role=user.getRole();
//		this.password = user.getPassword();
		this.deleted = user.isDeleted();
		this.address = user.getAddress();
		this.mobileNumber = user.getMobileNumber();
		this.imageName = user.getImageName();
	}
	
	/**
	 * Parameterized UserDTO constructor
	 * 
	 * @param firstName
	 * @param lastName
	 * @param role
	 * @param email
	 * @param address
	 * @param imageName
	 * @param mobileNumber
	 * @param deleted
	 */

	public UserDTO(String firstName, String lastName, String role, String email, String address, String imageName,
			String mobileNumber, boolean deleted,String password) {
		this.firstName=firstName;
		this.lastName=lastName;
		this.role=role;
		this.email=email;
		this.address=address;
		this.imageName=imageName;
		this.mobileNumber=mobileNumber;
		this.password=password;
		
	}
}
