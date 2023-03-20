package com.restaurant.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.restaurant.enums.EmailStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class MailHistory extends BaseClass {

	@Column(name = "to_email")
	private String to;
	@Column(name = "from_email")
	private String from;

	private String fromName;

	private String subject;

	private String mailContent;

	private Date sentTime;

	@Enumerated(EnumType.STRING)
	private EmailStatus status;

	public MailHistory() {
		this.status = EmailStatus.PENDING;
	}

}
