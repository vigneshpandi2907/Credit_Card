package com.creditcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.creditcard")
public class CreditCardTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditCardTransactionApplication.class, args);
//		LatePaymentServiceImpl service = ctx.getBean(LatePaymentServiceImpl.class);
//		service.calculateInterestBeforePaymentDueDate();
	}

}
