//package com.restaurant.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.restaurant.entity.JwtRequest;
//import com.restaurant.entity.JwtResponse;
//import com.restaurant.service.impl.JwtService;
//import com.restaurant.util.JwtUtil;
//
//@RestController
//public class JwtController {
//	
////	@Autowired
////	private AuthenticationManager authenticationManager;
//	
//	@Autowired
//	private JwtService JwtService;
//	
//	@Autowired
//	private JwtUtil jwtUtil;
//	
////	@PostMapping("/authenticate")
////	public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception{
////		return JwtService.createJwtToken(jwtRequest);
////	}
//	
////	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
////	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
////		System.out.println(jwtRequest);
////		try {
////			this.authenticationManager.authenticate(
////					new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getUserPassword()));
////
////		} catch (UsernameNotFoundException e) {
////			e.printStackTrace();
////			throw new Exception("Bad Credentials");
////		} catch (BadCredentialsException e) {
////			e.printStackTrace();
////			throw new Exception("Bad Credentials");
////		}
////
////		// fine area..
////		UserDetails userDetails = this.JwtService.loadUserByUsername(jwtRequest.getUserName());
////		String token = this.jwtUtil.generateToken(userDetails);
////		System.out.println("JWT " + token);
////
////		// {"token":"value"}
////		return ResponseEntity.ok(new JwtResponse(token));
////	}
//}
