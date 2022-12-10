package com.creditcard.entity;

import java.util.Date;

public interface StatementDetailsCustomized {

	public String getCustomerName();

	public Long getCardNumber();

	public Double getCreditCardLimit();

	public Double getAvailableCreditLimit();

	public Date getStatementDate();

	public Date getPaymentDate();

}
