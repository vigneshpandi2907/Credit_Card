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
	private Integer accounId;

	@Column(name = "card_number", nullable = false)
	private Long cardNumber;

	@Column(name = "credit_limit", nullable = false)
	private Double creditLimit;

	@Column(name = "available_credit_limit", nullable = false)
	private Double availableCreditLimit;

	@Column(name = "last_bill_amount", nullable = false)
	private Double lastBillAmount;

	@Column(name = "statement_date", updatable = true)
	private Date statementDate;

	@Column(name = "Payment_date", updatable = true)
	private Date PaymentDate;

	@Column(name = "credit_card_id", nullable = false)
	private Integer creditCardId;

	@Column(name = "scheduled_payment_frequency", nullable = false)
	private String scheduledPaymentFrequency;

	@Column(name = "payment_status", nullable = false)
	private Character paymentStatus;

	@Column(name = "account_status", nullable = false)
	private String accountStatus;

	@CreationTimestamp
	@Column(name = "created_date", updatable = false)
	private Date createdTime;

	@UpdateTimestamp
	@Column(name = "update_date", updatable = true)
	private Date updatedTime;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "credit_card_id", nullable = false, referencedColumnName = "credit_card_id")
	private CreditCardDetails creditCardDetails;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "accountDetails")
	private List<StatementDetails> statementDetails;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "accountDetails")
	private List<TransactionDetails> transactionDetails;
}

//foreign key (customer_id) REFERENCES customer_details(customer_id),
