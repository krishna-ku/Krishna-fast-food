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
import com.restaurant.repository.CouponRepo;
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
	private CouponRepo couponRepo;

	@Autowired
	private RestaurantRepo restaurantRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private MenuRepo menuRepo;

	@Autowired
	private CronServices cronServices;

//	@Autowired
//	private ApplicationEventPublisher eventPublisher;

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

		Set<String> set = new HashSet<>();

		if (restaurant.getStatus().equals("CLOSE"))
			throw new BadRequestException("Restaurant is closed please order when restaurant is open");

		List<OrderItem> orderItems = orderDto.getOrderItems().stream().map(o -> {

			if (set.contains(o.getName())) {
				return null;
			}
			set.add(o.getName());
			OrderItem orderItem = new OrderItem(o);

//			Menu menu = this.menuRepo.findByName(o.getName())
//					.orElseThrow(() -> new ResourceNotFoundException(Keywords.MENU, Keywords.MENU_ID, o.getMenuId()));
			Menu menu = this.menuRepo.findByName(o.getName());
			if (menu == null) {
				throw new ResourceNotFoundException(Keywords.MENU, Keywords.MENU, o.getName());
			} else {
				orderItem.setMenu(menu);
			}

			orderItem.setOrder(order);
			return orderItem;

		}).filter(Objects::nonNull).collect(Collectors.toList());

		order.setOrderItems(orderItems);

		float totalPrice = 0f;
		for (OrderItem orderItem : order.getOrderItems()) {
			totalPrice += orderItem.getMenu().getPrice() * orderItem.getItemQuantity();
		}

//		float totalPrice = order.getOrderItems().stream().map(o -> o.getMenu().getPrice() * o.getItemQuantity())
//				.reduce(0f, Float::sum);
		Float totalPriceWithGST = totalPrice + (totalPrice * 0.18f);
		Float totalPriceAfterDiscount = 0f;

		String applyCoupon = orderDto.getApplyCoupon();

		if (applyCoupon != null) {

			Coupon userCoupon = couponRepo.findCouponBycodeAndUserId(applyCoupon, userId);

			if (userCoupon == null)
				throw new BadRequestException("Please enter valid coupon");

			else if (userCoupon.getExpireDate().before(new Date())) {
				userCoupon.setCouponStatus(CouponStatus.EXPIRED);
				couponRepo.save(userCoupon);
				throw new BadRequestException("Coupon is expired");
			}

			else if (totalPrice <= userCoupon.getMinPrice())
				throw new BadRequestException("please order minimum 100 rupees order from our restro thank you");

			else if (userCoupon.getCouponStatus().equals(CouponStatus.ACTIVE)) {
				totalPriceAfterDiscount = totalPriceWithGST * (userCoupon.getMinPercentage() / 100.0f);
				userCoupon.setCouponStatus(CouponStatus.REDEEM);
			}

			else {
				throw new BadRequestException("Coupon is already used!!");
			}
		}

		order.setTotalPrice(totalPrice);
		order.setTotalPriceWithGst(totalPriceWithGST);
		order.setTotalPriceAfterDiscount(totalPriceAfterDiscount);
		order.setApplyCoupon(orderDto.getApplyCoupon());
		order.setStatus(OrderStatus.WAITING);
		order.setUser(user);
		order.setRestaurant(restaurant);
//		orderRepo.save(order);
		saveOrderNumber(order);

		CompletableFuture.runAsync(() -> {

			byte[] createPdf = PdfGenerator.createPdf(order);

			emailService.sendOrderMailToUser("Order Placed", user.getEmail(), user.getFirstName(), createPdf);
		});

		log.info("Order placed successfully by user {}", user.getFirstName());

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
//			eventPublisher.publishEvent(new orderPlacedEvent(order));
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
	public PagingDTO<OrderDTO> getAllOrders(Integer pageNumber, Integer pageSize) {

		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<Order> page = orderRepo.findAllNotDeletedOrders(pageable);
		List<Order> orders = page.getContent();
		List<OrderDTO> orderDTOs = orders.stream().map(OrderDTO::new).collect(Collectors.toList());
		return new PagingDTO<>(orderDTOs, page.getTotalElements(), page.getTotalPages());
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

//		orderRepo.findbyu

		Specification<Order> specification = Specification
				.where(OrderSpecification.filterOrders(orderDTO, userName, authorities));

		return orderRepo.findAll(specification).stream().map(o -> new OrderDTO(o)).collect(Collectors.toList());
//		List<Order> userOrders = orderRepo.getUserOrders(userName, specification);
//		return userOrders.stream().map(o -> new OrderDTO(o)).collect(Collectors.toList());
//		return orderRepo.getUserOrders(specification).stream().map(o-> new OrderDTO(o)).collect(Collectors.toList());
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
		Order order = orderRepo.findByIdAndUsername(orderID, username)// check this by query orderId and userId
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.ORDER, Keywords.ORDER_ID, orderID));

		return placedOrder(new OrderDTO(order), user.getId());
	}

//	public void testOrder() {
//		List<Order> orderrs=orderRepo.testOrder(100);
//		Set<String> u=new HashSet<>();
//		for(Order o:orderrs) {
//			String firstName = o.getUser().getFirstName();
//			u.add(firstName);
//		}
//		for(String s:u) {
//			
//			System.out.println(s);
//		}

//		List<String> nameList=orderrs
}
