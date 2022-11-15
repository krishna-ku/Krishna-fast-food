//package com.restaurant.Configuration;
//
//import java.io.IOException;
//import java.text.ParseException;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//import com.restaurant.util.JwtUtil;
//
//import lombok.SneakyThrows;
//
//
//public class CustomAuthorizationFilter extends BasicAuthenticationFilter {
//
//    @Value("${jwt.key:HjksWEds$31")
//    private String jwtKey;
//
//    public CustomAuthorizationFilter(AuthenticationManager authenticationManager) {
//        super(authenticationManager);
//    }
//
//    @SneakyThrows
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String header = request.getHeader("Authorization");
//        if (header == null || !header.startsWith("Bearer")) {
//            chain.doFilter(request, response);
//            return;
//        }
//        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        chain.doFilter(request, response);
//    }
//
//
//    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws ParseException {
//        String token = request.getHeader("Authorization");
//        if (token != null) {
//            // parse the token.
//            Map<String, Object> verify = JwtUtil.getAllClaimsFromToken(token.substring(7));
//            String username = verify.get("sub").toString();
//            if (username != null) {
//                return new UsernamePasswordAuthenticationToken(username, null, getAuthorities("user"));
//            }
//            return null;
//        }
//        return null;
//    }
//
//    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
//        return Arrays.asList(new SimpleGrantedAuthority(role));
//    }
//
//}
