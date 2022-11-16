package com.restaurant.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.restaurant.enums.OrderStatus;

import lombok.Data;

@Data
@Entity(name = "Orders")
public class Order extends SuperClass {
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	// public Map<String, String> menuMap=new HashMap<>();

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	private String customer;
	
	@OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
	List<OrderItem> orderItems=new ArrayList<>();
	
	
}
