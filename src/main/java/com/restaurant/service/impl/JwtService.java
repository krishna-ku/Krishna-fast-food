package com.restaurant.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
					getAuthority(user));
		} else {
			throw new UsernameNotFoundException("User not found with this username" + username);
		}
	}

	private Set<SimpleGrantedAuthority> getAuthority(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		Arrays.asList(user.getRole().split(",")).forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role));
		});
		return authorities;}
	
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
