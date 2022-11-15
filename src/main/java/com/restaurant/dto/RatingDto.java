package com.restaurant.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.restaurant.entity.User;

import lombok.Data;

@Data
public class RatingDto {

	private long id;
	
	@Range(min = 1, max = 5, message = "Rating between 1 to 5")
	private int rating;
	
	@Size(min = 0, max = 100, message = "Review minimum between 10 to 100 !!")
	private String review;
	
	private User user;
}
