package com.restaurant.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.restaurant.enums.OrderStatus;

import lombok.Data;

@Data
@Entity
//@Entity(name = "Orders")
@Table(name = "orders")
@SQLDelete(sql = "UPDATE orders SET deleted=true WHERE id=?")
@Where(clause = "deleted=false")
public class Order extends BaseClass {

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	// public Map<String, String> menuMap=new HashMap<>();

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	@Column(columnDefinition = "bigint default 1000",unique = true)
	private long orderNumber;

//	private String customer;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	List<OrderItem> orderItems = new ArrayList<>();

	@OneToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

}
