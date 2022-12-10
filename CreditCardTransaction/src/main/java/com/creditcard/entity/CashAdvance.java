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
@Table(name = "cash_advance")
@EntityListeners(AuditingEntityListener.class)
public class CashAdvance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cash_advance_id", nullable = false)
	private Integer cashAdvanceId;

	@Column(name = "cash_advance_processing_amount", nullable = false)
	private Double cashAdvanceProcessingAmount;

	@Column(name = "interest_amount", nullable = false)
	private Double interestAmount;

	@Column(name = "cash_advance_fee", nullable = false)
	private Double cashAdvanceFee;

	@Column(name = "processing_date", nullable = false)
	private Date processingDate;

	@Column(name = "flag", nullable = false)
	private String flag;

	@CreationTimestamp
	@Column(name = "created_date", updatable = false)
	private Date createdDate;

	@UpdateTimestamp
	@Column(name = "updated_date", updatable = true)
	private Date updatedDate;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "transaction_id", nullable = false, referencedColumnName = "transaction_id")
	private TransactionDetails transactionDetails;

	public Integer getCashAdvanceId() {
		return cashAdvanceId;
	}

	public void setCashAdvanceId(Integer cashAdvanceId) {
		this.cashAdvanceId = cashAdvanceId;
	}

	public Double getCashAdvanceProcessingAmount() {
		return cashAdvanceProcessingAmount;
	}

	public void setCashAdvanceProcessingAmount(Double cashAdvanceProcessingAmount) {
		this.cashAdvanceProcessingAmount = cashAdvanceProcessingAmount;
	}

	public Double getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(Double interestAmount) {
		this.interestAmount = interestAmount;
	}

	public Double getCashAdvanceFee() {
		return cashAdvanceFee;
	}

	public void setCashAdvanceFee(Double cashAdvanceFee) {
		this.cashAdvanceFee = cashAdvanceFee;
	}

	public Date getProcessingDate() {
		return processingDate;
	}

	public void setProcessingDate(Date processingDate) {
		this.processingDate = processingDate;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public TransactionDetails getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(TransactionDetails transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

}