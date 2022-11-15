package com.restaurant.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
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
}
