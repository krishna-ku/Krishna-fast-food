package com.restaurant.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

import org.hibernate.annotations.SQLDelete;

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

	private int minPrice;

	private float minPercentage;

	private Date expireDate;

	private String userEmail;

	@OneToOne
	private User user;
}
