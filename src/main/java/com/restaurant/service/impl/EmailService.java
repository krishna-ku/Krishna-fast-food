package com.restaurant.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {
	
	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private TemplateEngine templateEngine;

	@Value("${spring.mail.username}")
	private String sender;

	public void sendMailToUser(String subject, @NotEmpty String email,String name) {
		try {
			
			log.info("sending mail on user sign up :: {} ", email);
			MimeMessage mailMessage = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
			Context context =  new Context();
			Map<String, Object> map=new HashMap<>();
			map.put("name",name);
			map.put("email",email);
			map.put("password","123456789");
			context.setVariables(map);
			String text=templateEngine.process("team-user-created", context);
			helper.addTo(email);
			helper.setSubject(subject);
			helper.setText(text, true);
			helper.setFrom(sender, "Delhi-fast-food");
			emailSender.send(mailMessage);
		} catch (Exception e) {
			log.error("Error while sending mail on user sign up :: {} ", e.getMessage());
		}
	}
	
	public void sendMailOrders(String subject, @NotEmpty String email,String name) {
		try {
			
			log.info("sending mail on user placing order :: {} ", email);
			MimeMessage mailMessage = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
			Context context =  new Context();
			Map<String, Object> map=new HashMap<>();
			map.put("name",name);
			map.put("email",email);
			map.put("password","123456789");
			context.setVariables(map);
			String text=templateEngine.process("team-order-created", context);
			helper.addTo(email);
			helper.setSubject(subject);
			helper.setText(text, true);
			helper.setFrom(sender, "Delhi-fast-food");
			emailSender.send(mailMessage);
		} catch (Exception e) {
			log.error("Error while sending mail on user order placed :: {} ", e.getMessage());
		}
	}


}