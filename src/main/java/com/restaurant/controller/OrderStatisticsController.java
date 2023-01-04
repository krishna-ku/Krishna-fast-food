package com.restaurant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.OrderStatistics;
import com.restaurant.repository.OrderRepo;

@RestController
@RequestMapping("/lastweekorders")
public class OrderStatisticsController {

	@Autowired
	private OrderRepo orderRepo;

	@GetMapping
	public List<OrderStatistics> getLastOneWeekOrders(@RequestParam String fromDate, @RequestParam String toDate) {
		return orderRepo.oneWeekOrders(fromDate, toDate);
	}
}
