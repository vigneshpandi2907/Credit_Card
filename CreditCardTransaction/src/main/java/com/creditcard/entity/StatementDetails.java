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
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "statemet_details")
@EntityListeners(AuditingEntityListener.class)
public class StatementDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "statemet_id")
	private Integer statemetId;

	@CreationTimestamp
	@Column(name = "statement_generated_date", updatable = false)
	private Date statementGeneratedDate;

	@Column(name = "total_bill_ammount", nullable = false)
	private Double totalBillAmmount;

	@Column(name = "overlimit_fee", nullable = false)
	private Double overlimitFee;

	@Column(name = "tax", nullable = false)
	private Double tax;

	@Column(name = "late_payment_fee", nullable = false)
	private Double latePaymentFee;

	@Lob
	@Column(name = "statement")
	private byte[] statement;

	@CreationTimestamp
	@Column(name = "Created_date", updatable = false)
	private Date createdTime;

	@UpdateTimestamp
	@Column(name = "Updated_date", updatable = true)
	private Date updatedTime;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id", nullable = false, referencedColumnName = "account_id")
	private AccountDetails accountDetails;

	public Integer getStatemetId() {
		return statemetId;
	}

	public void setStatemetId(Integer statemetId) {
		this.statemetId = statemetId;
	}

	public Date getStatementGeneratedDate() {
		return statementGeneratedDate;
	}

	public void setStatementGeneratedDate(Date statementGeneratedDate) {
		this.statementGeneratedDate = statementGeneratedDate;
	}

	public Double getTotalBillAmmount() {
		return totalBillAmmount;
	}

	public void setTotalBillAmmount(Double totalBillAmmount) {
		this.totalBillAmmount = totalBillAmmount;
	}

	public Double getOverlimitFee() {
		return overlimitFee;
	}

	public void setOverlimitFee(Double overlimitFee) {
		this.overlimitFee = overlimitFee;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getLatePaymentFee() {
		return latePaymentFee;
	}

	public void setLatePaymentFee(Double latePaymentFee) {
		this.latePaymentFee = latePaymentFee;
	}

	public byte[] getStatement() {
		return statement;
	}

	public void setStatement(byte[] statement) {
		this.statement = statement;
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

	public AccountDetails getAccountDetails() {
		return accountDetails;
	}

	public void setAccountDetails(AccountDetails accountDetails) {
		this.accountDetails = accountDetails;
	}

}
