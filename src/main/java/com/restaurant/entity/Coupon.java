package com.restaurant.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.Range;

import com.restaurant.enums.CouponStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE Coupon SET deleted=true where id=?")
public class Coupon extends BaseClass {

	private String couponCode;

	@Enumerated(EnumType.STRING)
	private CouponStatus couponStatus;

	@Range(min = 100, message = "please give minimum price at lease 100")
	private float minPrice;

	@Range(min = 5, max = 100, message = "Please give minimum percentage between 5% to 100%")
	private float minPercentage;

	@NotNull
	private Date expireDate;

	private String userEmail;

	@OneToOne
	private User user;
}
