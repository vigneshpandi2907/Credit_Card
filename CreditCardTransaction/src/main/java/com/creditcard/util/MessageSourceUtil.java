package com.creditcard.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageSourceUtil {

	@Autowired
	@Qualifier("messageSourceBn")
	private MessageSource messageSource;

	private static final String errorBaseKey = "ERROR_CONFIG";
	private static final String errorSeperator = ".";
	private static final String errorMessage = "message";
	private static final String baseKey = errorBaseKey + errorSeperator;

	public String getLocalisedText(String errorCode, String module) {

		return messageSource.getMessage(baseKey + module + errorSeperator + errorCode + errorSeperator + errorMessage,
				new Object[0], LocaleContextHolder.getLocale());

	}

	@Bean(name = "messageSourceBn")
	private MessageSource businessConstantsProperties() {
		ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
		reloadableResourceBundleMessageSource.setBasename("classpath:/messages/errors/errorMessages");
		return reloadableResourceBundleMessageSource;
	}
}
