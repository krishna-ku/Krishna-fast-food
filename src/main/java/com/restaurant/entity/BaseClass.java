package com.restaurant.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;

@Data
@MappedSuperclass
public class BaseClass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Date createdOn;
	private Date updatedOn;
	@Column(columnDefinition = "boolean default false")
	private boolean deleted;
	// private String createdBy;
	// private String updatedBy;

	@PrePersist
	void beforePersist() {
		this.deleted = false;
		this.createdOn = new Date();
	}

	@PreUpdate
	void preUpdateDateTime() {
		this.updatedOn = new Date();
	}

}
