package com.restaurant.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.repository.MenuRepo;
import com.restaurant.repository.OrderItemRepo;
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

//	@Autowired
//	private OrderItemRepo orderItemRepo;

	/**
	 * Placed Order.
	 * 
	 * @param List<OrderItemDto>
	 * @return OrderDto
	 * @see com.restaurant.dto.OrderDto
	 */
//	@Override
//	public OrderDto placedOrder(OrderDto orderDto) {
//		
////		List<OrderItem> orderItems =orderDto.getOrderItems().stream().map(itemdto->new OrderItem()).collect(Collectors.toList());
////		Order order=new Order();
////		order.setOrderItems(orderItems);
////		order.setOrderStatus("Waiting");
////		orderRepo.save(order);
//		
//		return null;
//	}

	public OrderDto placedOrder(List<OrderItemDto> orderItemDto,long id) {


		User user=userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException(Keywords.USER, Keywords.USER_ID, id));
		
		Order order = new Order();
		List<OrderItem> orderItems = orderItemDto.stream().map(o -> {
			OrderItem orderItem = new OrderItem(o);
			
//			Menu menu=this.menuRepo.findById(o.getMenuId()).orElseThrow(() ->new ResourceNotFoundException("Menu", "Menu id", 0));
//			orderItem.setMenu(menu);
			
			orderItem.setOrder(order);
			return orderItem;

		}).collect(Collectors.toList());
		order.setStatus(OrderStatus.WAITING);
		order.setOrderItems(orderItems);
		order.setUser(user);
		orderRepo.save(order);

//		OrderDto orderDto = new OrderDto(order);
//		OrderDto orderDto = new OrderDto(order);

//		OrderItem orderItem=new OrderItem();
////		orderItem.setMenu(menu);
//		orderItem.setItemQuantity(orderItemDto.getItemQuantity());
//		orderItemRepo.save(orderItem);
		
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

		Order order = orderRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "Order id", id));

		if (order != null) {

			order.setStatus(OrderStatus.WAITING);
			List<OrderItem> orderItems = order.getOrderItems();
			Map<Long, OrderItem> collect = orderItems.stream().collect(Collectors.toMap(OrderItem::getId, Function.identity()));//search id etc directly//menuid 
			List<OrderItem> updatedOrderItemList=new ArrayList<>();
//			List<OrderItem> deleteOrderItemList=new ArrayList<>();
			for (OrderItemDto orderItemDto : orderItemList) {
				
				if(collect.containsKey(orderItemDto.getId()))//get menuId 
					{
					OrderItem item = collect.get(orderItemDto.getId());
					item.setItemQuantity(orderItemDto.getItemQuantity());
					updatedOrderItemList.add(item);
				}else {
					OrderItem newOrderItem=new OrderItem(orderItemDto);
					newOrderItem.setOrder(order);
					updatedOrderItemList.add(newOrderItem);
					
				}
			}
			order.setOrderItems(updatedOrderItemList);
			orderRepo.save(order);
			
			return  new OrderDto(order);

		}

		return null;
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

//		List<Order> orders = orderRepo.findAll();
//		OrderDto orderDto = orders.stream().map(o->new )
//				List<OrderDto> orderItems = orders.stream().map(o -> {
//					OrderItem orderItem = new OrderItem(o);
//					return orderItem;
//
//				}).collect(Collectors.toList());

		return null;
	}

	/**
	 * This function is return Order by id
	 * 
	 * @param id
	 * @return Order by id
	 */
	@Override
	public OrderDto getOrderById(Long id) {
		return null;
	}

	/**
	 * convert OrderDto object into Order
	 * 
	 * @param OrderDto
	 * @return Order
	 */
//	private Order dtoToOrder(OrderDto orderDto) {
//		Order order = new Order();
//		order.setOrderStatus(orderDto.getOrderStatus());
//		return order;
//	}

	/**
	 * convert Order object into OrderDto
	 * 
	 * @param Order
	 * @return OrderDto
	 */
//	private OrderDto orderToDto(Order order) {
//		OrderDto orderDto = new OrderDto();
//		orderDto.setOrderStatus(order.getOrderStatus());
//		return orderDto;
//	}

}
