package com.restaurant.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.restaurant.dto.UserDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(indexes = @Index(name = "idx_user_firstName", columnList = "firstName")) // for indexing
@NoArgsConstructor
@SQLDelete(sql = "UPDATE user SET deleted=true WHERE id=?")
@Where(clause = "deleted=false")
public class User extends BaseClass {

	private String firstName;
	private String lastName;

	private String email;

	private String address;

	private String mobileNumber;

	private String imageName;

	private String role;

	private String password;

//	@Override
//	public String toString() {
//		return "User [email=" + email + "]";
//	}

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<Order> orders = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<Rating> rating = new ArrayList<>();

	public User(UserDTO userDto) {
		this.firstName = userDto.getFirstName();
		this.lastName = userDto.getLastName();
		this.role = userDto.getRole();
		this.email = userDto.getEmail();
		this.password = userDto.getPassword();
		this.address = userDto.getAddress();
		this.mobileNumber = userDto.getMobileNumber();
		this.imageName = userDto.getImageName();
	}
//
//	@OneToOne
//	private EmailDetails emailDetails;

}
