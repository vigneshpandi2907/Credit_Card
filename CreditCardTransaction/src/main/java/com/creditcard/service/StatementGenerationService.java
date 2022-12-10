package com.creditcard.service;

import com.creditcard.response.Status;

/*
 * interface
 */
public interface StatementGenerationService {

	/**
	 * generate statement
	 * 
	 * @return
	 */
	public Status generatingStatement();

}
