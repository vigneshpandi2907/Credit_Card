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
@Table(name = "credit_card_details")
@EntityListeners(AuditingEntityListener.class)
public class CreditCardDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "credit_card_id")
	private Integer creditCardId;

	@Column(name = "credit_card_type", nullable = false)
	private String creditCardType;

	@Column(name = "interest_free_period", nullable = false)
	private Integer interestFreePeriod;

	@Column(name = "minimum_repayment_percentage", nullable = false)
	private Double minimum_repayment_percentage;

	@Column(name = "minimum_repayment_amount", nullable = false)
	private Double minimumRepaymentAmount;

	@Column(name = "annual_fee", nullable = false)
	private Double annualFee;

	@Column(name = "joining_fee", nullable = false)
	private Double joiningFee;

	@Column(name = "interest_rate", nullable = false)
	private Double interestRate;

	@Column(name = "grace_days", nullable = false)
	private Integer grace_days;

	@Column(name = "cash_advance_fee", nullable = false)
	private Double cashAdvanceFee;

	@Column(name = "convertion_of_emi", nullable = false)
	private Double convertionOfEmi;

	@Column(name = "tax_presentage", nullable = false)
	private Double taxPresentage;

	@CreationTimestamp
	@Column(name = "Created_date", updatable = false)
	private Date createdTime;

	@UpdateTimestamp
	@Column(name = "Update_date", updatable = true)
	private Date updatedTime;

}
