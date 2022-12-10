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

	@Column(name = "transaction_amount", nullable = false)
	private Double transactionAmount;

	@CreationTimestamp
	@Column(name = "transaction_date", updatable = false)
	private Date transactionDate;

	@Column(name = "transaction_code", nullable = false)
	private String transactionCode;

	@Column(name = "transaction_type", nullable = false)
	private String transactionType;

	@Column(name = "transactoin_description", nullable = false)
	private String transactoinDescription;

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
