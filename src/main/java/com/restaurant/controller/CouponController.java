package com.restaurant.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.entity.Coupon;
import com.restaurant.service.impl.CouponServiceImpl;

@RestController
public class CouponController {

	@Autowired
	private CouponServiceImpl couponServiceImpl;

//	@Autowired
//	private CronServices cronServices;

	/**
	 * creat coupons and save in database
	 * 
	 * @param coupon
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@PostMapping("/createcoupons")
	public ResponseEntity<Void> createCouponsForUsers(@RequestBody Coupon coupon,
			@RequestParam(required = false) Integer orderNumber, @RequestParam(required = false) Date date) {
		couponServiceImpl.createDiscountCoupons(coupon, orderNumber, date);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * send coupon mails to all users
	 * 
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@PostMapping("/sendcoupons")
	public ResponseEntity<Void> sendCouponsToUsers() {
//		cronServices.generateCouponsByCronJob();
		couponServiceImpl.sendDiscountCouponsMailToUsers();
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
