package com.creditcard.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "late_payment_charge")
@EntityListeners(AuditingEntityListener.class)
public class LatePaymentCharge {
	@Column(name = "minimum_balance", nullable = false)
	private Double minimumBalance;

	@Column(name = "maximum_balance", nullable = false)
	private Double maximumBalance;

	@Column(name = "charges", nullable = false)
	private Double charges;

	@CreationTimestamp
	@Column(name = "Created_date", updatable = false)
	private Date createdTime;

	@UpdateTimestamp
	@Column(name = "Update_date", updatable = true)
	private Date updatedTime;
}