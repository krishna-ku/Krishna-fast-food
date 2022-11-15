//package com.restaurant.controller;
//
//import java.util.List;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.restaurant.dto.ApiResponse;
//import com.restaurant.dto.OrderDto;
//import com.restaurant.dto.OrderItemDto;
//import com.restaurant.service.OrderItemService;
//
//@RestController
//@RequestMapping("/order")
//public class OrderItemController {
//
//	@Autowired
//	private OrderItemService orderItemService;
//
//	/**
//	 * Add OrderItems
//	 * service url : /order
//	 * method : Post
//	 * @param OrderItemDto
//	 * @return OrderItemDto 
//	 * @see com.restaurant.dto.OrderItemDto
//	 */
////	@PostMapping
////	public OrderDto placeOrder(@Valid @RequestBody List<OrderItemDto> orderItemDto) {
////		orderItemDto.forEach(o -> System.out.println(o));
////		return orderItemService.placedOrder(orderItemDto);
////
////	}
//	
//	/**
//	 * Update OrderItem by id
//	 * service url: /order/id
//	 * method : PUT
//	 * @param id
//	 * @param OderItemDto
//	 * @return Updated OrderItemDto {@link com.restaurant.dto.OrderItemDto}
//	 */
////	@PutMapping("/{id}")
////	public OrderItemDto updateOrder(@RequestBody OrderItemDto orderItemDto,@PathVariable long id) {
////		return this.orderItemService.updateOrder(orderItemDto, id);
////	}
//	
//	/**
//	 * Delete OrderItem by id
//	 * Method : DELETE
//	 * Service url: /order/id
//	 * @param id
//	 * 
//	 */
//	@DeleteMapping("/{id}")
//	public ResponseEntity<ApiResponse> deleteOrder(@PathVariable long id){
//		this.orderItemService.deleteOrder(id);
//		return new ResponseEntity<>(new ApiResponse("Order delete successfully",true),HttpStatus.OK);
//	}
//	
//	/**
//	 * get list of OrderItem
//	 * Service url: /order
//	 * method :  GET
//	 * @return list of User {@link com.restaurant.entity.OrderItem}
//	 */
//	@GetMapping
//	public ResponseEntity<List<OrderItemDto>> getAllOrders(){
//		 List<OrderItemDto> allOrders = orderItemService.getAllOrders();
//		return new ResponseEntity<>(allOrders,HttpStatus.OK);
//	}
//	
//	/**
//	 * get detail of OrderItems by id
//	 * Service url: /order/id
//	 * method: GET
//	 * @param id
//	 * @return OrderDto
//	 * @see com.restaurant.dto.OrderDto
//	 */
//	@GetMapping("/{id}")
//	public ResponseEntity<OrderItemDto> getOrderById(@PathVariable long id){
//		
//		return ResponseEntity.ok(orderItemService.getOrderById(id));
//		
//	}
//	
//	
//	
//	
//	
//	
//}
