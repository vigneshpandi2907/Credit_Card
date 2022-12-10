package com.creditcard.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "account_details")
@EntityListeners(AuditingEntityListener.class)
public class AccountDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private Integer accountId;

	@Column(name = "card_number", nullable = false)
	private Long cardNumber;

	@Column(name = "credit_limit", nullable = false)
	private Double creditLimit;

	@Column(name = "available_credit_limit", nullable = false)
	private Double availableCreditLimit;

	@Column(name = "last_bill_amount", nullable = false)
	private Double lastBillAmount;

	@Column(name = "statement_date")
	private Date statementDate;

	@Column(name = "Payment_date")
	private Date PaymentDate;

	@Column(name = "scheduled_payment_frequency", nullable = false)
	private String scheduledPaymentFrequency;

	@Column(name = "payment_status", nullable = false)
	private Character paymentStatus;

	@CreationTimestamp
	@Column(name = "start_date", updatable = false)
	private Date StartDateTime;

	@CreationTimestamp
	@Column(name = "created_date", updatable = false)
	private Date createdTime;

	@UpdateTimestamp
	@Column(name = "updated_date", updatable = true)
	private Date updatedTime;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "credit_card_id", nullable = false, referencedColumnName = "credit_card_id")
	private CreditCardDetails creditCardDetails;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "accountDetails")
	private List<StatementDetails> statementDetails;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "accountDetails")
	private List<TransactionDetails> transactionDetails;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "accountDetails")
	private List<LateInterest> lateInterest;

	public Date getStartDateTime() {
		return StartDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		StartDateTime = startDateTime;
	}

	public List<LateInterest> getLateInterest() {
		return lateInterest;
	}

	public void setLateInterest(List<LateInterest> lateInterest) {
		this.lateInterest = lateInterest;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(Long cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}

	public Double getAvailableCreditLimit() {
		return availableCreditLimit;
	}

	public void setAvailableCreditLimit(Double availableCreditLimit) {
		this.availableCreditLimit = availableCreditLimit;
	}

	public Double getLastBillAmount() {
		return lastBillAmount;
	}

	public void setLastBillAmount(Double lastBillAmount) {
		this.lastBillAmount = lastBillAmount;
	}

	public Date getStatementDate() {
		return statementDate;
	}

	public void setStatementDate(Date statementDate) {
		this.statementDate = statementDate;
	}

	public Date getPaymentDate() {
		return PaymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		PaymentDate = paymentDate;
	}

	public String getScheduledPaymentFrequency() {
		return scheduledPaymentFrequency;
	}

	public void setScheduledPaymentFrequency(String scheduledPaymentFrequency) {
		this.scheduledPaymentFrequency = scheduledPaymentFrequency;
	}

	public Character getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Character paymentStatus) {
		this.paymentStatus = paymentStatus;
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

	public CreditCardDetails getCreditCardDetails() {
		return creditCardDetails;
	}

	public void setCreditCardDetails(CreditCardDetails creditCardDetails) {
		this.creditCardDetails = creditCardDetails;
	}

	public List<StatementDetails> getStatementDetails() {
		return statementDetails;
	}

	public void setStatementDetails(List<StatementDetails> statementDetails) {
		this.statementDetails = statementDetails;
	}

	public List<TransactionDetails> getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(List<TransactionDetails> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

}
