package com.restaurant.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.restaurant.enums.EmailStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class MailHistory extends BaseClass {

	private String to;
	private String from;
	
	private String fromName;
	
	private String subject;

	private String mailContent;

	private Date sentTime;

	@Enumerated(EnumType.STRING)
	private EmailStatus status;
	
	public MailHistory() {
		this.status=EmailStatus.PENDING;
	}

}
