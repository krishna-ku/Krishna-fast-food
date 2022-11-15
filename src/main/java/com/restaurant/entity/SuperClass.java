package com.restaurant.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import lombok.Data;

@Data
@MappedSuperclass
public class SuperClass {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	private long createdOn;
	private long modifiedOn;//updatedOn
	@Column(columnDefinition = "boolean default false")
	private boolean deleted;
	//private String createdBy;
	//private String updatedBy;

	@PrePersist
	void beforePersist() {
		this.deleted=false;
		this.createdOn=System.currentTimeMillis();
	}
}
