package com.restaurant.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.restaurant.entity.Coupon;
import com.restaurant.entity.MailHistory;
import com.restaurant.entity.User;
import com.restaurant.enums.CouponStatus;
import com.restaurant.enums.EmailStatus;
import com.restaurant.exception.BadRequestException;
import com.restaurant.repository.CouponRepo;
import com.restaurant.repository.MailHistoryRepo;
import com.restaurant.repository.UserRepo;

@Service
public class CouponServiceImpl {

	@Value("${spring.mail.username}")
	private String sender;

	@Autowired
	private MailHistoryRepo mailHistoryRepo;

	@Autowired
	private CouponRepo couponRepo;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserRepo userRepo;

	/**
	 * generate random coupon codes
	 */
//	public String generateCouponCode() {
//
////		Coupon coupon = new Coupon();
////		String random = RandomStringUtils.random(8);
////		coupon.setCouponCode(random);
////		coupon.setCouponStatus(CouponStatus.ACTIVE);
////		couponRepo.save(coupon);
//		return RandomStringUtils.randomAlphanumeric(8);
//
//	}

	/**
	 * create discount coupons and save in database
	 */
//	@Scheduled(cron = "1 1 0 * * SAT")
	public void createDiscountCoupons(Coupon couponObject) {

		List<User> users = userRepo.findAll();

		List<MailHistory> list = new LinkedList<>();

		List<Coupon> coupons = new LinkedList<>();

		String subject = "Discount coupon";

		for (User user : users) {
			MailHistory mailHistory = new MailHistory();
			mailHistory.setTo(user.getEmail());
			mailHistory.setFrom(sender);
//			emailDetailsRepo.save(emailDetails);
			Coupon coupon = new Coupon();
//			String couponCode = generateCouponCode();
			String couponCode = "Delhi60";
			coupon.setCouponCode(couponCode);
			coupon.setCouponStatus(CouponStatus.ACTIVE);
			coupon.setUserEmail(user.getEmail());
			coupon.setUser(user);
			coupon.setMinPrice(couponObject.getMinPrice());
			coupon.setMinPercentage(couponObject.getMinPercentage());
			coupon.setExpireDate(couponObject.getExpireDate());

			coupon.getExpireDate();
//			Date expireDate = coupon.getExpireDate();
//			Date currentDate = new Date();
//			if (expireDate.before(currentDate)) {
//				coupon.setCouponStatus(CouponStatus.EXPIRED);
//			}
			mailHistory.setSubject(subject);
//			couponRepo.save(coupon);
			String content = "<h1>Dear " + user.getFirstName() + " " + user.getLastName()
					+ "</h1> <h2> Use this coupon code: <span style='color:red;'>" + couponCode
					+ "</span> to get Discount on your order</h2>";
			mailHistory.setMailContent(content);
			coupons.add(coupon);
			list.add(mailHistory);
		}
		if (!coupons.isEmpty())
			couponRepo.saveAll(coupons);

		if (!list.isEmpty()) {
			mailHistoryRepo.saveAll(list);
		}
	}

	/**
	 * send coupon mails to all users
	 */
	@Scheduled(cron = "1 2 0 * * SAT")
	public void sendDiscountCouponsMailToUsers() {

		List<MailHistory> mailHistories = mailHistoryRepo.findAll();

		if (mailHistories != null) {
			for (MailHistory mailHistory : mailHistories) {

				String userEmail = mailHistory.getTo();
				String mailContent = mailHistory.getMailContent();

				mailHistory.setSentTime(new Date());

				boolean emailStatus = emailService.sendCouponCodeEamil(userEmail, mailContent);
				if (emailStatus == true)
					mailHistory.setStatus(EmailStatus.SENT);
				else {
					mailHistory.setStatus(EmailStatus.FAILED);
				}
				mailHistoryRepo.save(mailHistory);

			}
		} else
			throw new BadRequestException("There is no coupons for users please create coupons first");
	}

//		while (!queue.isEmpty()) {
//			MailHistory emailDetails = queue.poll();
//			String email = emailDetails.getTo();
//
//			boolean sent = emailService.sendCouponCodeEamil(email, null, couponCode);
//			if (sent == true)
//				emailDetails.setStatus(EmailStatus.SENT);
////			emailDetails.setSentTime();
//			else {
//				emailDetails.setStatus(EmailStatus.FAILED);
//			}
//
//		}

//		for(User user:users) {
//			Coupon coupon=new Coupon();
//			String couponCode = generateCouponCode();
//			coupon.setCouponStatus(CouponStatus.ACTIVE);
//			coupon.setCouponCode(couponCode);
//			coupon.setUser(user);
//			coupon.setUserEmail(user.getEmail());
//			couponRepo.save(coupon);
//			String email = user.getEmail();
//			String firstName = user.getFirstName();
//			emailService.sendCouponCodeEamil(email, firstName, couponCode);
//		}
//	}

}
