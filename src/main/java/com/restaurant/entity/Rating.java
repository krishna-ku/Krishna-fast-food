package com.restaurant.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.restaurant.dto.RatingDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE Rating SET deleted=true WHERE id=?")
//@Where(clause = "deleted=false")
@FilterDef(name = "deletedRatingFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedRatingFilter", condition = "deleted = :isDeleted")
public class Rating extends BaseClass {

	// @Column(name = "Rating out of 5")
	private int rating;
	private String review;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	@OneToOne
	@JoinColumn(name = "order_Id")
	private Order orders;

	public Rating(RatingDto ratingDto) {
		this.rating = ratingDto.getRating();
		this.review = ratingDto.getReview();
	}
}
