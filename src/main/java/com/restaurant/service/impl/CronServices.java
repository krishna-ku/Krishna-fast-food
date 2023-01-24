package com.restaurant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CronServices {

	@Autowired
	private CouponServiceImpl couponServiceImpl;

	/**
	 * send coupon to user on email by cron job every saturday
	 * 
	 * @param void
	 * @return void
	 */
	@Scheduled(cron = "1 2 0 * * SAT")
	public void generateCouponsByCronJob() {

		couponServiceImpl.sendDiscountCouponsMailToUsers();

	}

}
