package com.restaurant.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;

import lombok.Data;

@Data
@MappedSuperclass
public class BaseClass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Date createdOn;
	private Date updatingOn;// updatedOn
	@Column(columnDefinition = "boolean default false")
	private boolean deleted;
	// private String createdBy;
	// private String updatedBy;

	@PrePersist
	void beforePersist() {
		this.deleted = false;
		this.createdOn = new Date();// preupdate for update for updatedOn
//		this.updatingOn=System.currentTimeMillis();
	}
//	@PostPersist
//	void postPersists() {
//		this.updatingOn=System.currentTimeMillis();
//	}
}
