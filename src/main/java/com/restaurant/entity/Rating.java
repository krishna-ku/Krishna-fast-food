package com.restaurant.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.restaurant.dto.RatingDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Rating extends SuperClass {
	
	
	//@Column(name = "Rating out of 5")
	private int rating;
	private String review;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	@OneToOne
	@JoinColumn(name = "order_Id")
	private Order orders;
	
	public Rating(RatingDto ratingDto) {
		this.rating=ratingDto.getRating();
		this.review=ratingDto.getReview();
	}
}
