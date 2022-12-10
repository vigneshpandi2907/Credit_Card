package com.creditcard.entity;

import java.util.Date;

public interface ITransactionCustomized {

	public Date getTransactionDate();

	public Integer getAccountId();

	public Date getProcessingDate();

	public Double getTransactionAmount();

	public String getTransactionCode();

	public String getTransactionType();

	public Double getBalanceAmount();

	public Double getInterestAmount();
}
