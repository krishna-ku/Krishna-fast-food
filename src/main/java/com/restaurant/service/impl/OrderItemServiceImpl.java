//package com.restaurant.service.impl;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.restaurant.dto.OrderDto;
//import com.restaurant.dto.OrderItemDto;
//import com.restaurant.entity.Menu;
//import com.restaurant.entity.Order;
//import com.restaurant.entity.OrderItem;
//import com.restaurant.exception.ResourceNotFoundException;
//import com.restaurant.repository.MenuRepo;
//import com.restaurant.repository.OrderItemRepo;
//import com.restaurant.repository.OrderRepo;
//import com.restaurant.service.OrderItemService;
//
//import lombok.AllArgsConstructor;
//import net.bytebuddy.asm.Advice.This;
//
//@Service
//@AllArgsConstructor
//public class OrderItemServiceImpl implements OrderItemService {
//
//	// @Autowired
//	private OrderItemRepo orderItemRepo;
//
//	private OrderRepo orderRepo;
//
////	@Autowired
//	private MenuRepo menuRepo;
//	
//	
//	/**
//	 * placed order.
//	 * @param OrderDto
//	 * @return OrderDto
//	 * @see com.restaurant.dto.OrderDto
//	 */
////	public OrderDto placedOrder(List<OrderItemDto> orderItemDto) {
////
//////		Menu menu=this.menuRepo.findById(menuid).orElseThrow(() -> new ResourceNotFoundException("Menu", "Menu id", menuid));
////
////		List<OrderItem> orderItems = orderItemDto.stream().map(o -> {
////			OrderItem orderItem = new OrderItem(o);
////			return orderItem;
////
////		}).collect(Collectors.toList());
////
////		Order order = new Order();
////		order.setOrderStatus("Waiting");
////		order.setOrderItems(orderItems);
////		order.setUser(null);
////		orderRepo.save(order);
////
////		OrderDto orderDto = new OrderDto(order);
////
//////		OrderItem orderItem=new OrderItem();
////////		orderItem.setMenu(menu);
//////		orderItem.setItemQuantity(orderItemDto.getItemQuantity());
//////		orderItemRepo.save(orderItem);
////
////		return orderDto;
////	}
//
//	
//	/**
//	 * update OrderItem.
//	 * 
//	 * @param OrderItemDto
//	 * @param orderItemDto
//	 * @param id
//	 * @return updated OrderItemDto
//	 * @see com.restaurant.dto.OrderItemDto
//	 */
////	@Override
////	public OrderItemDto updateOrder(OrderItemDto orderItemDto, long id) {
////
////		OrderItem orderItem = orderItemRepo.findById(id)
////				.orElseThrow(() -> new ResourceNotFoundException("OrderItem", "OrderItem id", id));
////		
////		//problem in update menu
////
////		// String str = Long.toString(orderItemDto.getMenuId());
////
////		if (!StringUtils.isEmpty(Long.toString(orderItemDto.getMenuId()))) {
////			orderItem.setMenuId(orderItemDto.getMenuId());
////		}
////
////		if (StringUtils.isEmpty(Integer.toString(orderItemDto.getItemQuantity()))) {
////			orderItem.setItemQuantity(orderItemDto.getItemQuantity());
////		}
////
////		// orderItemDto.setId(orderItem.getId(id));
////
////		orderItemRepo.save(orderItem);
////
////		return orderItemDto;
////	}
//
//	/**
//	 * delete OrderItem
//	 * 
//	 * @param id
//	 * @return void
//	 */
//	@Override
//	public void deleteOrder(long id) {
//
//		OrderItem orderItem = this.orderItemRepo.findById(id)
//				.orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id", id));
//		this.orderItemRepo.delete(orderItem);
//	}
//
//	/**
//	 * return all OrderItems present in OrderItem
//	 * 
//	 * @return list of OrderItem
//	 * @see com.restaurant.entity.OrderItem
//	 */
//	@Override
//	public List<OrderItemDto> getAllOrders() {
//
//		List<OrderItem> orderItems = this.orderItemRepo.findAll();
//		
//		List<OrderItemDto> orderItemDtos=orderItems.stream().map(orderItem->this.OrderItemToDto(orderItem)).collect(Collectors.toList());
//
//		return orderItemDtos;
//
//		//return orderItems.stream().map(dtoItems -> new OrderItemDto()).collect(Collectors.toList());
//	}
//
//	/**
//	 * find OrderIem by id
//	 * 
//	 * @param id
//	 * @return OrderItem by id
//	 * @see com.restaurant.entity.OrderItem
//	 */
//	@Override
//	public OrderItemDto getOrderById(long id) {
//
//		OrderItem orderItem=orderItemRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Order", "Order Id", id));
//		
//		return OrderItemToDto(orderItem);
//	}
//
//	/**
//	 * convert OrderItemDto into OrderItemDto class
//	 * 
//	 * @param OrderItemDto
//	 * @return OrderItem
//	 * @see com.restaurant.entity.OrderItem
//	 */
//	private OrderItem dtoToOrderItem(OrderItemDto orderItemDto) {
//		OrderItem orderItem = new OrderItem();
//		orderItem.setMenuId(orderItemDto.getMenuId());
//		orderItem.setItemQuantity(orderItemDto.getItemQuantity());
//		return orderItem;
//	}
//
//	/**
//	 * convert OrderItm into OrderItemDto class
//	 * 
//	 * @param OrderItem
//	 * @return OrderItemDto
//	 * @see com.restaurant.dto.OrderItemDto
//	 */
//	private OrderItemDto OrderItemToDto(OrderItem orderItem) {
//		OrderItemDto orderItemDto = new OrderItemDto();
//		orderItemDto.setMenuId(orderItem.getMenuId());
//		orderItemDto.setId(orderItem.getId());
//		orderItemDto.setItemQuantity(orderItem.getItemQuantity());
//		return orderItemDto;
//	}
//
//}
