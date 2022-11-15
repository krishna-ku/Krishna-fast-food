package com.restaurant.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

import lombok.Data;
@Data
public class MenuDto  {
	
	private long id;
	@NotEmpty
	private String name;

//	@NotNull
	@Range(min = 1,max = 999)//we use range for integer and float type variables
	//@Size(min = 1,max = 3,message = "price between 1 to 999")//we cannot use this on integer and float etc
	private float price;//float or double

	@NotEmpty
	@Pattern(regexp = "[A-Za-Z]{10-100}",message = "size must be between 10 to 100 alphabets")
	private String description;//size validation
}
