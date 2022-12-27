package com.restaurant.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
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

	/**
	 * Send mail to user when user is created account
	 * 
	 * @param subject
	 * @param email
	 * @param name
	 */
	public void sendAccountCreatedMailToUser(String subject, @NotEmpty String email, String name) {
		try {

			log.info("sending mail on user sign up :: {} ", email);
			MimeMessage mailMessage = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
			Context context = new Context();
			Map<String, Object> map = new HashMap<>();
			map.put("name", name);
			map.put("email", email);
			map.put("password", "123456789");
			context.setVariables(map);
			String text = templateEngine.process("user", context);
			helper.addTo(email);
			helper.setSubject(subject);
			helper.setText(text, true);
			helper.setFrom(sender, "Delhi-fast-food");
			emailSender.send(mailMessage);
		} catch (Exception e) {
			log.error("Error while sending mail on user sign up :: {} ", e.getMessage());
		}
	}

	/**
	 * Send mail to user when user placed order
	 * 
	 * @param subject
	 * @param email
	 * @param name
	 */
	public void sendOrderMailToUser(String subject, @NotEmpty String email, String name,byte[] byteArray) {
		try {

			log.info("sending mail on user placing order :: {} ", email);
			MimeMessage mailMessage = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mailMessage,true);
			Context context = new Context();
			Map<String, Object> map = new HashMap<>();
			map.put("name", name);
			map.put("email", email);
//			map.put("password","123456789");
			context.setVariables(map);
			String text = templateEngine.process("order-template", context);
			helper.addTo(email);
			helper.setSubject(subject);
			helper.setText(text, true);
			helper.setFrom(sender, "Delhi-fast-food");
			
//			String path="C:\\Users\\user\\Desktop\\restro.pdf";
			
//			FileSystemResource file=new FileSystemResource(new File(path));
			ByteArrayResource byteArrayResource=new ByteArrayResource(byteArray);
			helper.addAttachment("bill.pdf", byteArrayResource);
			
			emailSender.send(mailMessage);
		} catch (Exception e) {
			log.error("Error while sending mail on user order placed :: {} ", e.getMessage());
		}
	}

}
