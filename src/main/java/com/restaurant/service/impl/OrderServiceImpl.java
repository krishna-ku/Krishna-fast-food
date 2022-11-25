package com.restaurant.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.dto.Keywords;
import com.restaurant.dto.OrderDto;
import com.restaurant.dto.OrderItemDto;
import com.restaurant.entity.Menu;
import com.restaurant.entity.Order;
import com.restaurant.entity.OrderItem;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.User;
import com.restaurant.enums.OrderStatus;
import com.restaurant.exception.BadRequestException;
import com.restaurant.exception.NullRequestException;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.repository.MenuRepo;
import com.restaurant.repository.OrderRepo;
import com.restaurant.repository.RestaurantRepo;
import com.restaurant.repository.UserRepo;
import com.restaurant.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private RestaurantRepo restaurantRepo;

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
	public OrderDto placedOrder(OrderDto orderDto, long userId) {

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.USER, Keywords.USER_ID, userId));

		Object keywords;
		Restaurant restaurant = restaurantRepo.findById((long) 1)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.RESTAURANT, Keywords.RESTAURANT_ID, 1));

		String customer = user.getFirstName() + " " + user.getLastName();

		Order order = new Order();

		Set<Long> set = new HashSet<>();

		List<OrderItem> orderItems = orderDto.getOrderItems().stream().map(o -> {

			if (set.contains(o.getMenuId())) {
				return null;
			}
			set.add(o.getMenuId());
			OrderItem orderItem = new OrderItem(o);

			Menu menu = this.menuRepo.findById(o.getMenuId())
					.orElseThrow(() -> new ResourceNotFoundException(Keywords.MENU, Keywords.MENU_ID, o.getMenuId()));
			orderItem.setMenu(menu);

			orderItem.setOrder(order);
			return orderItem;

		}).filter(Objects::nonNull).collect(Collectors.toList());

		order.setStatus(OrderStatus.WAITING);
		order.setOrderItems(orderItems);
		order.setUser(user);
		order.setRestaurant(restaurant);
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
	public OrderDto updateOrder(List<OrderItemDto> orderItemList, Long orderId) {

		Order order = orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.ORDER, Keywords.ORDER_ID, orderId));

		if (order != null) {

			order.setStatus(OrderStatus.WAITING);
			List<OrderItem> orderItems = order.getOrderItems();
			Map<Long, OrderItem> collect = orderItems.stream()
					.collect(Collectors.toMap(o -> o.getMenu().getId(), Function.identity()));
			List<OrderItem> updatedOrderItemList = new ArrayList<>();
//			List<OrderItem> deleteOrderItemList=new ArrayList<>();
			for (OrderItemDto orderItemDto : orderItemList) {

				if (orderItemDto.getItemQuantity() > 10 || orderItemDto.getItemQuantity() < 1)
					throw new BadRequestException("Item quantity should not be less than 1 or more than 10");

				if (collect.containsKey(orderItemDto.getMenuId())) {
					OrderItem item = collect.get(orderItemDto.getMenuId());
					item.setItemQuantity(orderItemDto.getItemQuantity());
					updatedOrderItemList.add(item);
				} else {
					Menu menu = menuRepo.findById(orderItemDto.getMenuId())
							.orElseThrow(() -> new ResourceNotFoundException(Keywords.MENU, Keywords.MENU_ID,
									orderItemDto.getMenuId()));
					if (menu != null) {
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

		throw new NullRequestException("menuId is not found please enter correct menuId");
	}

	/**
	 * delete Order
	 * 
	 * @param id
	 * @return void
	 */
	@Override
	public void deleteOrder(long orderId) {

		this.orderRepo.deleteById(orderId);

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
		return orders.stream().map(OrderDto::new).collect(Collectors.toList());
	}

	/**
	 * This function is return Order by id
	 * 
	 * @param id
	 * @return Order by id
	 */
	@Override
	public OrderDto getOrderById(Long orderId) {

		Order order = orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.ORDER, Keywords.ORDER_ID, orderId));
		return new OrderDto(order);
	}

	/**
	 * return lists of deleted and undeleted orders
	 * 
	 * @param isDeleted=true or false
	 * @return list of deleted or undeleted orders
	 * @see com.restaurant.entity.orders
	 */
	public List<OrderDto> findAllFilter(boolean isDeleted) {
		Session session = entityManager.unwrap(Session.class);
		Filter filter = session.enableFilter("deletedOrderFilter");
		filter.setParameter("isDeleted", isDeleted);
		List<Order> orders = orderRepo.findAll();
		session.disableFilter("deletedOrderFilter");

		return orders.stream().map(u -> new OrderDto(u)).collect(Collectors.toList());
	}

}
