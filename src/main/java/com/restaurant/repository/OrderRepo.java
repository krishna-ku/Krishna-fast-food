package com.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

}
