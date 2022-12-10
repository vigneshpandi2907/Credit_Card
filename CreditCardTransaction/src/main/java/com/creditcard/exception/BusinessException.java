package com.creditcard.exception;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.springframework.http.HttpStatus;

import com.creditcard.constants.ApplicationConstants;

public class BusinessException extends BaseException {

	private static final long serialVersionUID = 1L;

	private int id;
	private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	private String status = ApplicationConstants.FAILURE;
	private String errorCode;
	private String errorModule;
	private String exceptionMessage;
	private String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
			.format(new Timestamp(System.currentTimeMillis()));

	public BusinessException(String errorCode, String errorModule, String exceptionMessage) {
		super();

		this.errorCode = errorCode;
		this.errorModule = errorModule;
		this.exceptionMessage = exceptionMessage;

	}

	public BusinessException() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorModule() {
		return errorModule;
	}

	public void setErrorModule(String errorModule) {
		this.errorModule = errorModule;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
