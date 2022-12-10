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
	@Column(name = "Update_date", updatable = true)
	private Date updatedTime;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id", nullable = false, referencedColumnName = "account_id")
	private AccountDetails accountDetails;

}
