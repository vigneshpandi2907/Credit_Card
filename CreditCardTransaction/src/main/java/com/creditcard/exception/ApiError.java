package com.creditcard.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ApiError {
	
	private int id;
    private String errorMessage;
    private String errorCode;
    private String timeStamp;
    private String exceptionMessage;
    private String status;
    private HttpStatus httpStatus;
    
	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	
	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}



	public ApiError(int id, String errorMessage, String errorCode, String timeStamp, String exceptionMessage, HttpStatus httpStatus,String status) {
		super();
		this.id = id;
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
		this.timeStamp = timeStamp;
		this.exceptionMessage = exceptionMessage;
		this.httpStatus= httpStatus;
		this.status=status;
	}
	
    
 
    
}
