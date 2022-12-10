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
	private Double minimumRepaymentPercentage;

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

	@Column(name = "tax_percentage", nullable = false)
	private Double taxPercentage;

	@CreationTimestamp
	@Column(name = "Created_date", updatable = false)
	private Date createdTime;

	@UpdateTimestamp
	@Column(name = "Updated_date", updatable = true)
	private Date updatedTime;

	public Integer getCreditCardId() {
		return creditCardId;
	}

	public void setCreditCardId(Integer creditCardId) {
		this.creditCardId = creditCardId;
	}

	public String getCreditCardType() {
		return creditCardType;
	}

	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}

	public Integer getInterestFreePeriod() {
		return interestFreePeriod;
	}

	public void setInterestFreePeriod(Integer interestFreePeriod) {
		this.interestFreePeriod = interestFreePeriod;
	}

	public Double getMinimumRepaymentAmount() {
		return minimumRepaymentAmount;
	}

	public void setMinimumRepaymentAmount(Double minimumRepaymentAmount) {
		this.minimumRepaymentAmount = minimumRepaymentAmount;
	}

	public Double getAnnualFee() {
		return annualFee;
	}

	public void setAnnualFee(Double annualFee) {
		this.annualFee = annualFee;
	}

	public Double getJoiningFee() {
		return joiningFee;
	}

	public void setJoiningFee(Double joiningFee) {
		this.joiningFee = joiningFee;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public Integer getGrace_days() {
		return grace_days;
	}

	public void setGrace_days(Integer grace_days) {
		this.grace_days = grace_days;
	}

	public Double getCashAdvanceFee() {
		return cashAdvanceFee;
	}

	public void setCashAdvanceFee(Double cashAdvanceFee) {
		this.cashAdvanceFee = cashAdvanceFee;
	}

	public Double getConvertionOfEmi() {
		return convertionOfEmi;
	}

	public void setConvertionOfEmi(Double convertionOfEmi) {
		this.convertionOfEmi = convertionOfEmi;
	}

	public Double getMinimumRepaymentPercentage() {
		return minimumRepaymentPercentage;
	}

	public void setMinimumRepaymentPercentage(Double minimumRepaymentPercentage) {
		this.minimumRepaymentPercentage = minimumRepaymentPercentage;
	}

	public Double getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(Double taxPercentage) {
		this.taxPercentage = taxPercentage;
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

}
