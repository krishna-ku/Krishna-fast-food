package com.restaurant.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.ApiResponse;
import com.restaurant.dto.OrderDTO;
import com.restaurant.dto.OrderItemDTO;
import com.restaurant.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	/**
	 * Add OrderItems service url : /order method : Post
	 * 
	 * @param List<OrderItemDto>
	 * @return OrderDto
	 * @see com.restaurant.dto.OrderDTO
	 */
	@PostMapping("/{userId}")
	public OrderDTO placeOrder(@RequestBody @Valid OrderDTO orderDto, @PathVariable long userId) {
		return orderService.placedOrder(orderDto, userId);

	}

	/**
	 * Update OrderItems by id service url: /order/id method : PUT
	 * 
	 * @param id
	 * @param List<OderItemDto?
	 * @return Updated OrderItemDto {@link com.restaurant.dto.OrderItemDTO}
	 */
	@PutMapping("/{orderId}")
	public OrderDTO updateOrder(@RequestBody List<OrderItemDTO> orderItemDto, @PathVariable long orderId) {
		orderItemDto.forEach(System.out::println);
		return orderService.updateOrder(orderItemDto, orderId);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@DeleteMapping("/{orderId}")
	public ResponseEntity<ApiResponse> deleteOrder(@PathVariable long orderId) {
		this.orderService.deleteOrder(orderId);
		return new ResponseEntity<>(new ApiResponse("Order is deleted successfully", true), HttpStatus.OK);
	}

	/**
	 * get list of all orders Service url: /orders method : GET
	 * 
	 * @return list of User {@link com.restaurant.entity.User}
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping
	public ResponseEntity<List<OrderDTO>> getAllOrders(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
		List<OrderDTO> allOrders = orderService.getAllOrders(pageNumber, pageSize);
		return new ResponseEntity<>(allOrders, HttpStatus.OK);
	}

	/**
	 * filter menus on basis of id,status
	 * 
	 * @param menuDTO
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping("/filter")
	public ResponseEntity<List<OrderDTO>> filterOrders(@RequestBody OrderDTO orderDTO) {
		List<OrderDTO> filterOrders = orderService.filterOrders(orderDTO);
		return new ResponseEntity<>(filterOrders, HttpStatus.OK);
	}

	/**
	 * Activate order
	 * 
	 * @param userId
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@PutMapping("/activate/{orderId}")
	public ResponseEntity<String> activateUserEntity(@PathVariable long orderId) {
		return ResponseEntity.ok(orderService.activateOrder(orderId));
	}

	@GetMapping("/rating")
	public ResponseEntity<List<OrderDTO>> getOrdersRating() {
		return ResponseEntity.ok(orderService.getOrdersByRating());
	}

}
