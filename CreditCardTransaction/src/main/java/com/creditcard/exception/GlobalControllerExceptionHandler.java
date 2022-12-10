package com.creditcard.exception;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.creditcard.constants.ApplicationConstants;
import com.creditcard.constants.ExceptionConstants;
import com.creditcard.util.MessageSourceUtil;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {
	@Autowired
	MessageSourceUtil messageSourceUtil;

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handleBusinessException(WebRequest request, Exception exception) {
		BaseException baseException = (BaseException) exception;
		return getCustomExceptionResponse(request, baseException);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(WebRequest request, Exception exception) {
		BaseException e = new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionConstants.GENERAL_ERROR_CODE,
				ExceptionConstants.GENERAL_MODULE, exception.getMessage(), ApplicationConstants.FAILURE);
		return getCustomExceptionResponse(request, e);
	}

	private ResponseEntity<Object> getCustomExceptionResponse(WebRequest request, BaseException baseException) {
		// TODO Auto-generated method stub
		String errorCode = baseException.getErrorCode();
		String exceptionMessage = baseException.getExceptionMessage();
		String timeStamp = baseException.getTimeStamp();
		String errorModule = baseException.getErrorModule();
		String errorMessage = "";
		Integer id = RandomUtils.nextInt(10000, 50000);
		String status = baseException.getStatus();
		HttpStatus httpStatus = baseException.getHttpStatus();

		try {
			errorMessage = messageSourceUtil.getLocalisedText(errorCode, errorModule);

		} catch (Exception e) {
			errorMessage = messageSourceUtil.getLocalisedText(ExceptionConstants.GENERAL_ERROR_CODE,
					ExceptionConstants.GENERAL_MODULE);
			exceptionMessage = "The message for errorCode:" + errorCode + " module:" + errorModule
					+ " is not found in prop file";
		}

		return new ResponseEntity<>(
				new ApiError(id, errorMessage, errorCode, timeStamp, exceptionMessage, httpStatus, status), httpStatus);
	}
}
