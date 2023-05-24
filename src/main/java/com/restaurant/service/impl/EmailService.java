package com.restaurant.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

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

	@Autowired
	private AmazonS3 s3;

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
	public void sendOrderMailToUser(String subject, @NotEmpty String email, String name, byte[] byteArray) {
		try {

			log.info("sending mail on user placing order :: {} ", email);
			MimeMessage mailMessage = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
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
			ByteArrayResource byteArrayResource = new ByteArrayResource(byteArray);
			helper.addAttachment("bill.pdf", byteArrayResource);

			// save bill pdf on AWS S3 restaurantbills bucket so customer can download his
			// bills when ever he wants
//			BillInvoicesDetails invoicesDetails=new BillInvoicesDetails();
			String fileName = "bill" + UUID.randomUUID().toString() + ".pdf";
//			invoicesDetails.setBillInvoice(fileName);
//			invoicesDetails.setEmailId(email);
//			invoicesDetails.setOrderNo(0);
			ObjectMetadata data = new ObjectMetadata();
			data.setContentType("application/pdf");
			data.setContentLength(byteArray.length);
			s3.putObject("restaurantbills", fileName, byteArrayResource.getInputStream(), data);

			emailSender.send(mailMessage);
		} catch (Exception e) {
			log.error("Error while sending mail on user order placed :: {} ", e.getMessage());
		}
	}

	/**
	 * send coupon code email to all users
	 * 
	 * @param email
	 * @param name
	 * @param couponcode
	 */
	public boolean sendCouponCodeEamil(String email, String mailContent) {
		try {
			log.info("sending coupon mails to user ");

			MimeMessage mailMessage = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
			Context context = new Context();
			Map<String, Object> map = new HashMap<>();
//			map.put("name", name);
			map.put("email", email);
			context.setVariables(map);
			helper.addTo(email);
			helper.setSubject("Discount coupon");
			helper.setText(mailContent, true);
//			helper.setText("<h1>Dear " + name + "</h1> <h2> Use this coupon code: <span style='color:red;'>"
//					+ couponcode + "</span> to get Discount on your order</h2>", true);
//			helper.setText(sender,"Delhi-fast-food");
			emailSender.send(mailMessage);

		} catch (Exception e) {
			log.error("Error while sending discount coupon mail to user :: {}", e.getMessage());
		}
		return true;
	}

}
