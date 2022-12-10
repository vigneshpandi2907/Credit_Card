package com.creditcard.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "late_payment_charge")
@EntityListeners(AuditingEntityListener.class)
public class LatePaymentCharge {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "late_payment_charge_id")
	private Integer latePaymentChargeId;

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
	@Column(name = "Updated_date", updatable = true)
	private Date updatedTime;

	public Double getMinimumBalance() {
		return minimumBalance;
	}

	public void setMinimumBalance(Double minimumBalance) {
		this.minimumBalance = minimumBalance;
	}

	public Double getMaximumBalance() {
		return maximumBalance;
	}

	public void setMaximumBalance(Double maximumBalance) {
		this.maximumBalance = maximumBalance;
	}

	public Double getCharges() {
		return charges;
	}

	public void setCharges(Double charges) {
		this.charges = charges;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public LatePaymentCharge(Double minimumBalance, Double maximumBalance, Double charges, Date createdTime,
			Date updatedTime) {
		super();
		this.minimumBalance = minimumBalance;
		this.maximumBalance = maximumBalance;
		this.charges = charges;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
	}

	public LatePaymentCharge() {
	}

	@Override
	public String toString() {
		return "LatePaymentCharge [minimumBalance=" + minimumBalance + ", maximumBalance=" + maximumBalance
				+ ", charges=" + charges + ", createdTime=" + createdTime + ", updatedTime=" + updatedTime + "]";
	}

}