package com.creditcard.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "late_interest")
@EntityListeners(AuditingEntityListener.class)
public class LateInterest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "late_interest_id")
	private Integer lateInterestId;

	@Column(name = "processing_date")
	private Date processingDate;

	@Column(name = "interest_amount")
	private Double interestAmount;

	@Column(name = "balance_amount")
	private Double balanceAmount;

	@Column(name = "cash_amount")
	private Double cashAmount;

	public Double getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(Double cashAmount) {
		this.cashAmount = cashAmount;
	}

	@CreationTimestamp
	@Column(name = "Created_date", updatable = false)
	private Date createdTime;

	@UpdateTimestamp
	@Column(name = "Updated_date", updatable = true)
	private Date updatedTime;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id", nullable = false, referencedColumnName = "account_id")
	private AccountDetails accountDetails;

	public LateInterest(Date processingDate, Double interestAmount, Double balanceAmount, Double cashAmount,
			AccountDetails accountDetails) {
		super();
		this.processingDate = processingDate;
		this.interestAmount = interestAmount;
		this.balanceAmount = balanceAmount;
		this.cashAmount = cashAmount;
		this.accountDetails = accountDetails;
	}

	public AccountDetails getAccountDetails() {
		return accountDetails;
	}

	public void setAccountDetails(AccountDetails accountDetails) {
		this.accountDetails = accountDetails;
	}

	public LateInterest() {

	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
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

	public Integer getLateInterestId() {
		return lateInterestId;
	}

	public void setLateInterestId(Integer lateInterestId) {
		this.lateInterestId = lateInterestId;
	}

	public Date getProcessingDate() {
		return processingDate;
	}

	public void setProcessingDate(Date processingDate) {
		this.processingDate = processingDate;
	}

	public Double getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(Double interestAmount) {
		this.interestAmount = interestAmount;
	}

}
