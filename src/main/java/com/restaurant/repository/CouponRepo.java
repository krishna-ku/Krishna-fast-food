package com.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.restaurant.entity.Coupon;

public interface CouponRepo extends JpaRepository<Coupon, Long> {

	@Query("select c from Coupon c where c.couponCode= :code and c.user.id=:userId")
	Coupon findCouponBycodeAndUserId(String code, long userId);

}
