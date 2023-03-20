package com.restaurant.configuration;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.restaurant.service.impl.TokenService;
import com.restaurant.util.JwtUtil;

import lombok.SneakyThrows;

public class CustomAuthorizationFilter extends BasicAuthenticationFilter {

	@Value("${jwt.key:HjksWEds$31}")
	private String jwtKey;

//	public static Map<String, String> tokenMap = new ConcurrentHashMap<>();

	public CustomAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@SneakyThrows
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith("Bearer")) {
			chain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
//		if (authentication != null)
//			tokenMap.put(authentication.getName(), header.substring(7));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws ParseException {
		String token = request.getHeader("Authorization");
		if (token != null) {
			// parse the token.
			token=token.substring(7);
			Map<String, Object> verify = JwtUtil.getAllClaimsFromToken(token);
			String username = verify.get("sub").toString();
			List<Map<String, String>> object = (List<Map<String, String>>) verify.get("authorities");
//			if (username != null && tokenMap.containsKey(username) && tokenMap.get(username).equals(token))
			if(username!=null && TokenService.tokeMap.containsKey(username) && TokenService.tokeMap.get(username).equals(token)){
				return new UsernamePasswordAuthenticationToken(username, null, getAuthorities(object));
			}
			return null;
		}
		return null;
	}

	private Collection<? extends GrantedAuthority> getAuthorities(List<Map<String, String>> authorities) {
		return authorities.stream().map(m -> new SimpleGrantedAuthority("ROLE_" + m.get("authority")))
				.collect(Collectors.toList());
	}
	
//	public void removeToken(String username) {
//		tokenMap.remove(username);
//	}
}
