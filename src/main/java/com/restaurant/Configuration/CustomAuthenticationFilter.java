//package com.restaurant.Configuration;
//
//import java.io.IOException;
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.Base64;
//import java.util.Date;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.util.StringUtils;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.restaurant.util.JwtUtil;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
////    private static final Map<String, String> validClient = new ConcurrentHashMap<>();
////
////    static {
////        validClient.put("clientName", "clientPassword");
////    }
//
//    private final AuthenticationManager authenticationManager;
//    @Value("${jwt.key:HjksWEds$31")
//    private String jwtKey;
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        try {
////            String clientHeader = request.getHeader("Authorization");
////            if (!StringUtils.containsWhitespace(clientHeader) || !clientHeader.startsWith("Basic")) {
////                throw new RuntimeException("unknown client");
////            }
////            String s = new String(Base64.getDecoder().decode(clientHeader.substring(6)));
////            String[] split = s.split(":");
////            if (split.length < 2 || !validClient.containsKey(split[0]) || !validClient.get(split[0]).equals(split[1])) {
////                throw new RuntimeException("invalid client");
////            }
//            Map<String, String> creds = new ObjectMapper()
//                    .readValue(request.getInputStream(), Map.class);
//            return authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            creds.get("username"),
//                            creds.get("password"),
//                            new ArrayList<>())
//            );
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
////    @SneakyThrows
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain
//            chain, Authentication auth) throws IOException, ServletException {
//        UserDetails principal = (UserDetails) auth.getPrincipal();
//        String token = null;
//        try {
//            token = JwtUtil.generateToken(principal);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        response.addHeader("Authorization", "Bearer " + token);
//    }
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                                                                       AuthenticationException failed)  throws IOException, ServletException {
//        failed.printStackTrace();
////        response.addHeader("Authorization", "Bearer " + token);
//    }
//}
