package com.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.entity.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {

}
