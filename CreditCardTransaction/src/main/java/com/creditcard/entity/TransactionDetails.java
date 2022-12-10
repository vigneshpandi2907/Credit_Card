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
@Table(name = "transaction_details")
@EntityListeners(AuditingEntityListener.class)
public class TransactionDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private Integer transactionId;
	@Column(name = "transaction_amount")
	private Double transactionAmount;
	@Column(name = "transaction_date")
	private Date transactionDate;
	@Column(name = "transaction_code")
	private String transactionCode;
	@Column(name = "transaction_type")
	private String transactionType;
	@Column(name = "transaction_description")
	private String transactoinDescription;

	@Column(name = "created_date")
	@CreationTimestamp
	private Date createdTime;

	@Column(name = "updated_date")
	@UpdateTimestamp
	private Date modifiedDate;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id", nullable = false, referencedColumnName = "account_id")
	private AccountDetails accountDetails;

	public AccountDetails getAccountDetails() {
		return accountDetails;
	}

	public void setAccountDetails(AccountDetails accountDetails) {
		this.accountDetails = accountDetails;
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public Double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactoinDescription() {
		return transactoinDescription;
	}

	public void setTransactoinDescription(String transactoinDescription) {
		this.transactoinDescription = transactoinDescription;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
	public String toString() {
		return "TransactionDetails [transactionId=" + transactionId + ", transactionAmount=" + transactionAmount
				+ ", transactionDate=" + transactionDate + ", transactionCode=" + transactionCode + ", transactionType="
				+ transactionType + ", transactoinDescription=" + transactoinDescription + ", createdTime="
				+ createdTime + ", modifiedDate=" + modifiedDate + "]";
	}

}
