package com.creditcard.dto;

public class TransactionDTO {
	private Double cerdit;
	private Double debit;

	public Double getCerdit() {
		return cerdit;
	}

	public void setCerdit(Double cerdit) {
		this.cerdit = cerdit;
	}

	public Double getDebit() {
		return debit;
	}

	public void setDebit(Double debit) {
		this.debit = debit;
	}

	public TransactionDTO() {

	}

	public TransactionDTO(Double cerdit, Double debit) {
		super();
		this.cerdit = cerdit;
		this.debit = debit;
	}

}
