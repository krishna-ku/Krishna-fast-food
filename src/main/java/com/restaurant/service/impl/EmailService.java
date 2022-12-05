package com.restaurant.service.impl;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	public void SendEmail(String subject, String text, @NotEmpty String email) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom(sender);

		message.setTo(email);

		message.setSubject(subject);

		message.setSentDate(new Date());

		message.setText(text);
		
		javaMailSender.send(message);

	}

}
