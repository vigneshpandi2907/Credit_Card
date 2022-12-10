package com.creditcard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.creditcard.response.Status;
import com.creditcard.service.StatementGenerationService;

@RestController
public class StatementGenerationController {

	@Autowired
	StatementGenerationService statementGenerationService;

	/**
	 * generate statement
	 * 
	 * @return
	 */
	@RequestMapping(value = "/generatingStatement", method = RequestMethod.GET)
	public Status generatingStatement() {
		return statementGenerationService.generatingStatement();
	}

}
