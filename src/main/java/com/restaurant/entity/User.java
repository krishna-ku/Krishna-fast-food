package com.restaurant.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;

import com.restaurant.dto.UserDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class User extends SuperClass {

	
	private String firstName;
	private String lastName;
	
//	@Column(unique = true)
//	@Email(message = "Email address is not valid !!",regexp = "[A-Za-z0-9._]+@gmail.com")
	private String email;
	
	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<Order> orders = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<Rating> rating = new ArrayList<>();

	
	
//	public User(String firstName, String lastName, String email, String password) {
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.password = password;
//	}
	public User(UserDto userDto) {
		this.firstName=userDto.getFirstName();
		this.lastName=userDto.getLastName();
		this.email=userDto.getEmail();
		this.password=userDto.getPassword();
	}

}
