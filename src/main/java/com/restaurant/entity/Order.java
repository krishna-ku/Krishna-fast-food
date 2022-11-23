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
import javax.persistence.OneToOne;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.restaurant.enums.OrderStatus;

import lombok.Data;

@Data
@Entity(name = "Orders")
@SQLDelete(sql = "UPDATE Order SET deleted=true WHERE id=?")
@Where(clause = "deleted=false")
public class Order extends BaseClass {

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	// public Map<String, String> menuMap=new HashMap<>();

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	private String customer;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	List<OrderItem> orderItems = new ArrayList<>();

	@OneToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

}
