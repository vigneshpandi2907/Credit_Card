package com.creditcard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.creditcard.response.Status;
import com.creditcard.service.ILatePaymentService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LatePaymentController {
	@Autowired
	ILatePaymentService latePaymentService;

	/**
	 * Calculate interest Before payment due date
	 * 
	 * @return message
	 */
	@RequestMapping(value = "/calculateCreditCardInterestBeforeDueDate", method = RequestMethod.GET)
	public Status calculateInterestBeforePaymentDueDate() {
		return latePaymentService.calculateInterestBeforePaymentDueDate();

	}

	/**
	 * Calculate interest after payment due date
	 * 
	 * @return message
	 */
	@RequestMapping(value = "/calculateCreditCardInterestAfterDueDate", method = RequestMethod.GET)
	public Status calculateInterestAfterPaymentDueDate() {
		return latePaymentService.calculateInterestAfterPaymentDueDate();

	}
}
