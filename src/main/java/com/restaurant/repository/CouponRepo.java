package com.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.entity.Coupon;

public interface CouponRepo extends JpaRepository<Coupon, Long> {

}
