package com.restaurant.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.dto.Keywords;
import com.restaurant.dto.OrderDto;
import com.restaurant.dto.OrderItemDto;
import com.restaurant.entity.Menu;
import com.restaurant.entity.Order;
import com.restaurant.entity.OrderItem;
import com.restaurant.entity.User;
import com.restaurant.enums.OrderStatus;
import com.restaurant.exception.NullRequestException;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.repository.MenuRepo;
import com.restaurant.repository.OrderRepo;
import com.restaurant.repository.UserRepo;
import com.restaurant.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private MenuRepo menuRepo;

	/**
	 * Placed Order.
	 * 
	 * @param List<OrderItemDto>
	 * @return OrderDto
	 * @see com.restaurant.dto.OrderDto
	 */
	public OrderDto placedOrder(List<OrderItemDto> orderItemDto, long id) {

		User user = userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.USER, Keywords.USER_ID, id));
		String customer = user.getFirstName() + " " + user.getLastName();// handle last name

		Order order = new Order();
		List<OrderItem> orderItems = orderItemDto.stream().map(o -> {
			
//			if(o.getItemQuantity()<10)
			
			OrderItem orderItem = new OrderItem(o);

			Menu menu = this.menuRepo.findById(o.getMenuId())
					.orElseThrow(() -> new ResourceNotFoundException(Keywords.MENU, Keywords.MENU_ID, o.getMenuId()));
			orderItem.setMenu(menu);

			orderItem.setOrder(order);
			return orderItem;

		}).collect(Collectors.toList());
		order.setStatus(OrderStatus.WAITING);
		order.setOrderItems(orderItems);
		order.setUser(user);
		order.setCustomer(customer);
		orderRepo.save(order);

		return new OrderDto(order);
	}

	/**
	 * update Order.
	 * 
	 * @param List<OrderItemDto>
	 * @param Orderid
	 * @return updated null if order is not updated else return updated order
	 * @see com.restaurant.dto.OrderDto
	 */
	@Override
	public OrderDto updateOrder(List<OrderItemDto> orderItemList, Long id) {

		Order order = orderRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.ORDER, Keywords.ORDER_ID, id));

		if (order != null) {

			order.setStatus(OrderStatus.WAITING);
			List<OrderItem> orderItems = order.getOrderItems();
			Map<Long, OrderItem> collect = orderItems.stream()
					.collect(Collectors.toMap(o -> o.getMenu().getId(), Function.identity()));
			List<OrderItem> updatedOrderItemList = new ArrayList<>();
//			List<OrderItem> deleteOrderItemList=new ArrayList<>();
			for (OrderItemDto orderItemDto : orderItemList) {

				if (collect.containsKey(orderItemDto.getMenuId())) {
					OrderItem item = collect.get(orderItemDto.getMenuId());
					item.setItemQuantity(orderItemDto.getItemQuantity());
					updatedOrderItemList.add(item);
				} else {
					Menu menu = menuRepo.findById(orderItemDto.getMenuId()).orElse(null);
					if(menu!=null) {
					OrderItem newOrderItem = new OrderItem(orderItemDto);
					newOrderItem.setOrder(order);
					newOrderItem.setMenu(menu);
					updatedOrderItemList.add(newOrderItem);
					
					}

				}
			}
			order.setOrderItems(updatedOrderItemList);
			orderRepo.save(order);

			return new OrderDto(order);

		}

		throw new NullRequestException("menuId is not found please enter correct menuId");//not working
	}

	/**
	 * delete Order
	 * 
	 * @param id
	 * @return void
	 */
	@Override
	public void deleteOrder(long id) {

		this.orderRepo.deleteById(id);

	}

	/**
	 * return all Orders
	 * 
	 * @return list of Orders
	 * @see com.restaurant.dto.OrderDto
	 */
	@Override
	public List<OrderDto> getAllOrders() {

		List<Order> orders = orderRepo.findAll();
//		return orders.stream().map(o-> new OrderDto(o)).collect(Collectors.toList());
		return orders.stream().map(OrderDto::new).collect(Collectors.toList());
	}

	/**
	 * This function is return Order by id
	 * 
	 * @param id
	 * @return Order by id
	 */
	@Override
	public OrderDto getOrderById(Long id) {

		Order order = orderRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.ORDER, Keywords.ORDER_ID, id));
		return new OrderDto(order);
	}

}
