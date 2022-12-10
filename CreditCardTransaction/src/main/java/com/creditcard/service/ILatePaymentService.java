package com.creditcard.service;

import com.creditcard.response.Status;

public interface ILatePaymentService {

	/**
	 * Calculate interest Before payment due date
	 * 
	 * @return message
	 */
	public Status calculateInterestBeforePaymentDueDate();

	/**
	 * Calculate interest after payment due date
	 * 
	 * @return message
	 */
	public Status calculateInterestAfterPaymentDueDate();
}
