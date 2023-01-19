package com.restaurant.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.restaurant.dto.Keywords;
import com.restaurant.dto.OrderDTO;
import com.restaurant.dto.OrderItemDTO;
import com.restaurant.dto.PagingDTO;
import com.restaurant.entity.Coupon;
import com.restaurant.entity.Menu;
import com.restaurant.entity.Order;
import com.restaurant.entity.OrderItem;
import com.restaurant.entity.Restaurant;
import com.restaurant.entity.User;
import com.restaurant.enums.CouponStatus;
import com.restaurant.enums.OrderStatus;
import com.restaurant.exception.BadRequestException;
import com.restaurant.exception.NullRequestException;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.pdfgenerator.PdfGenerator;
import com.restaurant.repository.MenuRepo;
import com.restaurant.repository.OrderRepo;
import com.restaurant.repository.RestaurantRepo;
import com.restaurant.repository.UserRepo;
import com.restaurant.service.OrderService;
import com.restaurant.specification.OrderSpecification;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private EmailService emailService;

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
	 * @see com.restaurant.dto.OrderDTO
	 */
	public OrderDTO placedOrder(OrderDTO orderDto, long userId) {

		log.info("Placing order for {} ", orderDto);

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.USER, Keywords.USER_ID, userId));

		if (StringUtils.isBlank(user.getAddress()) || StringUtils.length(user.getMobileNumber()) != 10)
			throw new BadRequestException("please first enter your mobile number and adress then try to placed order");

		Restaurant restaurant = restaurantRepo.findById((long) 1)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.RESTAURANT, Keywords.RESTAURANT_ID, 1));

		String customer = user.getFirstName() + " " + user.getLastName();

		Order order = new Order();

		Set<Long> set = new HashSet<>();

		if (restaurant.getStatus().equals("CLOSE"))
			throw new BadRequestException("Restaurant is closed please order when restaurant is open");

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

		Coupon coupon = new Coupon();

		if (order.getApplyCoupon() != null && user.getEmail() == coupon.getUserEmail()
				&& coupon.getCouponStatus().equals("ACTIVE")) {

			if (coupon.getExpireDate().before(new Date()))
				coupon.setCouponStatus(CouponStatus.EXPIRED);

			float totalPrice = orderDto.getTotalPrice();

			if (totalPrice >= coupon.getMinPrice())
				throw new BadRequestException("please order minimum 100 rupees order from our restro thank you");

			order.getApplyCoupon();

		}

		order.setStatus(OrderStatus.WAITING);
		order.setOrderItems(orderItems);
		order.setUser(user);
		order.setRestaurant(restaurant);
//		orderRepo.save(order);
		saveOrderNumber(order);

		CompletableFuture.runAsync(() -> {

			byte[] createPdf = PdfGenerator.createPdf(order);

			emailService.sendOrderMailToUser("Order Placed", user.getEmail(), user.getFirstName(), createPdf);
		});

		log.info("Order placed successfully");

		return new OrderDTO(order);
	}

	/**
	 * set order number and increment by 1 every time
	 * 
	 * @param order
	 */
	public void saveOrderNumber(Order order) {
		synchronized (this) {
			Long lastOrderNumber = orderRepo.findLastOrderNumber();
			order.setOrderNumber(lastOrderNumber + 1);
			orderRepo.save(order);
		}
	}

	/**
	 * update Order.
	 * 
	 * @param List<OrderItemDto>
	 * @param Orderid
	 * @return updated null if order is not updated else return updated order
	 * @see com.restaurant.dto.OrderDTO
	 */
	@Override
	public OrderDTO updateOrder(List<OrderItemDTO> orderItemList, Long orderId) {

		log.info("Updating order for {}", orderItemList);

		Order order = orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.ORDER, Keywords.ORDER_ID, orderId));

		if (order != null) {

			order.setStatus(OrderStatus.WAITING);
			List<OrderItem> orderItems = order.getOrderItems();
			Map<Long, OrderItem> collect = orderItems.stream()
					.collect(Collectors.toMap(o -> o.getMenu().getId(), Function.identity()));
			List<OrderItem> updatedOrderItemList = new ArrayList<>();
//			List<OrderItem> deleteOrderItemList=new ArrayList<>();
			for (OrderItemDTO orderItemDto : orderItemList) {

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

			log.info("Order updated successfully");

			return new OrderDTO(order);

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

		log.info("Deleting order for {} ", orderId);

		this.orderRepo.deleteById(orderId);
		log.info("Order deleted successfully");

	}

	/**
	 * return all Orders
	 * 
	 * @return list of Orders
	 * @see com.restaurant.dto.OrderDTO
	 */
//	@Override
	public PagingDTO getAllOrders(Integer pageNumber, Integer pageSize) {

		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<Order> page = orderRepo.findAll(pageable);
		List<Order> orders = page.getContent();
		List<OrderDTO> orderDTOs = orders.stream().map(o -> new OrderDTO(o)).collect(Collectors.toList());
		PagingDTO pagingDTO = new PagingDTO(orderDTOs, page.getTotalElements(), page.getTotalPages());
		return pagingDTO;
	}

	/**
	 * filter orders on the basis of id,status
	 * 
	 * @param menuDTO
	 * @return
	 */
	@Override
	public List<OrderDTO> filterOrders(OrderDTO orderDTO, String userName,
			Collection<? extends GrantedAuthority> authorities) {

		Specification<Order> specification = Specification
				.where(OrderSpecification.filterOrders(orderDTO, userName, authorities));
		return orderRepo.findAll(specification).stream().map(o -> new OrderDTO(o)).collect(Collectors.toList());
	}

	/**
	 * activate the deleted order
	 * 
	 * @param userId
	 * @return String
	 * @see com.restaurant.entity.User
	 */
	@Override
	public String activateOrder(long orderId) {

		Order order = orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.ORDER, Keywords.ORDER_ID, orderId));
		order.setDeleted(false);
		orderRepo.save(order);
		return "Order is activate";
	}

	/**
	 * Repeat user previous order which order user wants to repeat like user 1 is
	 * order 6 times and he wants to repeat order 4 then he need to pass its userId
	 * and OrderId
	 * 
	 * @param userId
	 * @param orderId
	 * @return OrderDTO
	 */
	public OrderDTO repeatOrder(String username, long orderID) {
		User user = userRepo.findByEmail(username);
		if (user == null) {
			throw new ResourceNotFoundException(Keywords.USER, Keywords.USER_ID, username);
		}
//			List<Order> previousOrders = user.getOrders();
//			Order repeat = previousOrders.stream().filter(order -> order.getId() == orderID).findFirst()
//					.orElseThrow(() -> new ResourceNotFoundException(Keywords.ORDER, Keywords.ORDER_ID, orderID));
//			OrderDTO newOrder = new OrderDTO(repeat);

//	        Order order = orderRepo.findById(orderID)//check this by query orderId and userId
		Order order = orderRepo.findByIdAndUsername(orderID, username)// check this by query orderId and userId
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.ORDER, Keywords.ORDER_ID, orderID));

//	        if (order.getUser().getEmail().equals(username)) {
//	            return placedOrder(new OrderDTO(order), user.getId());
//	        }
//
//	        throw new BadRequestException("Order not found");
		return placedOrder(new OrderDTO(order), user.getId());
	}

//	@Override
//	public List<OrderDTO> getOrdersByRating() {
////		Sort sort = null;
////		if (sortDir.equalsIgnoreCase("asc"))
////			sort.by(sortBy).ascending();
////		else
////			sort.by(sortBy).descending();
//
//		List<Order> orders = orderRepo.findAll();
//
//		return orders.stream().map(o -> new OrderDTO(o)).collect(Collectors.toList());
//
//	}

}
