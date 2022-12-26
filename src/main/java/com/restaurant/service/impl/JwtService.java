package com.restaurant.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.restaurant.entity.JwtRequest;
import com.restaurant.entity.JwtResponse;
import com.restaurant.entity.User;
import com.restaurant.repository.UserRepo;
import com.restaurant.util.JwtUtil;

@Service
public class JwtService implements UserDetailsService {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserRepo userRepo;
	
//	@Autowired
//	private AuthenticationManager authenticationManager;
//	
//	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception{
//		String userName=jwtRequest.getUserName();
//		String userPassword=jwtRequest.getUserPassword();
//		authenticate(userName, userPassword);
//		
//		UserDetails userDetails=loadUserByUsername(userName);
//		String genratedToken=jwtUtil.generateToken(userDetails);
//		
////		User user=this.userRepo.findByEmail(userName);
//		return new JwtResponse(genratedToken);
//	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = this.userRepo.findByEmail(username);

		if (user != null) {
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority("USER")));
		} else {
			throw new UsernameNotFoundException("User not found with this username" + username);
		}
	}
	
//	private Set getAuthority(User user) {
//		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//		user.getRole().forEach(role -> {
//			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
//		});
//		return authorities;
	
//	private void authenticate(String userName,String userPassword) throws Exception{
//		
//		try {
//			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
//		}catch (DisabledException e) {
//			throw new Exception("USER_DISABLED",e);	
//		}catch (BadCredentialsException e) {
//			throw new Exception(" INVALID_CREDENTIALS ",e);
//		}
//		
//	}

}
