package com.restaurant.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.ApiResponse;
import com.restaurant.dto.OrderDto;
import com.restaurant.dto.OrderItemDto;
import com.restaurant.service.OrderService;

//@Validated //todo
@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * Add OrderItems
	 * service url : /order
	 * method : Post
	 * @param List<OrderItemDto>
	 * @return OrderDto
	 * @see com.restaurant.dto.OrderDto
	 */
	@PostMapping("/{userId}")
	public OrderDto placeOrder(@RequestBody @Valid OrderDto orderDto,@PathVariable long userId) {
//		orderItemDto.forEach(System.out::println);
		return orderService.placedOrder(orderDto,userId);

	}
	
	/**
	 * Update OrderItems by id
	 * service url: /order/id
	 * method : PUT
	 * @param id
	 * @param List<OderItemDto?
	 * @return Updated OrderItemDto {@link com.restaurant.dto.OrderItemDto}
	 */
	@PutMapping("/{orderId}")
	public OrderDto updateOrder(@RequestBody List<OrderItemDto> orderItemDto,@PathVariable long orderId) {
		orderItemDto.forEach(System.out::println);
		//orderItemDto.forEach(o -> System.out.println(o));
		return orderService.updateOrder(orderItemDto, orderId);
	}
	
	@DeleteMapping("/{orderId}")
	public ResponseEntity<ApiResponse> deleteOrder(@PathVariable long orderId){
		this.orderService.deleteOrder(orderId);
		return new ResponseEntity<>(new ApiResponse("Order is deleted successfully",true),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<OrderDto>> getAllOrders(){
		List<OrderDto> allOrders = orderService.getAllOrders();
		return new ResponseEntity<>(allOrders,HttpStatus.OK);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderDto> getOrderById(@PathVariable long orderId) {
		
		return ResponseEntity.ok(orderService.getOrderById(orderId));
		
	}
	
	
	
	
	
	
	
	
	
}
