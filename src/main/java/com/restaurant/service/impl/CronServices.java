package com.restaurant.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.restaurant.enums.OrderStatus;
import com.restaurant.repository.OrderRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CronServices {

	@Autowired
	private CouponServiceImpl couponServiceImpl;
	
	@Autowired
	private OrderRepo orderRepo;

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
	
	@Transactional//do this work by procedure
	@Scheduled(fixedRate = 60000)
	public void changeOrderStatus() {
		
	    int updatedRows = orderRepo.updateOrderStatus(OrderStatus.WAITING, OrderStatus.IN_PROCESS,OrderStatus.COMPLETED);
//	    log.info("Updated {} orders from WAITING to IN_PROCESS", updatedRows);
	    
//	    updatedRows = orderRepo.updateOrderStatus(OrderStatus.IN_PROCESS, OrderStatus.COMPLETED);
	    log.info("Updated {} orders from WAITING TO IN_PROCESS and IN_PROCESS to COMPLETED", updatedRows);		
//		for(Order o:orders) {//we don't want to use loop here max 2 queries 
//			if(o.getStatus()==OrderStatus.WAITING)
//				o.setStatus(OrderStatus.IN_PROCESS);
//			else if(o.getStatus()==OrderStatus.IN_PROCESS)
//				o.setStatus(OrderStatus.COMPLETED);
//			orderRepo.save(o);
//		}
	}

}
