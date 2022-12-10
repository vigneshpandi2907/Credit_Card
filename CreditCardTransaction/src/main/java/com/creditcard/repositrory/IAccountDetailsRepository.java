package com.creditcard.repositrory;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.creditcard.entity.AccountDetails;
import com.creditcard.entity.ITransactionCustomized;

public interface IAccountDetailsRepository extends JpaRepository<AccountDetails, Integer> {

	// before paymentDate
	@Query(value = "select AD from AccountDetails AD join TransactionDetails TD on  AD.accountId = TD.accountDetails where AD.paymentStatus=:paymentStatus and TD.transactionType!='cash'")
	public List<AccountDetails> fetchAccountDetails(@Param("paymentStatus") char paymentStatus);

	@Modifying
	@Query(value = "update account_details set payment_status='P',statement_date=:nextStatementDate, payment_date=:nextPaymentDate ,available_credit_limit=:availableCreditLimit , start_date=:startDate where account_id=:accountId", nativeQuery = true)
	public void updateAccountDetails(@Param("nextStatementDate") Date nextStatmentDate,
			@Param("nextPaymentDate") Date nextPaymentDate, @Param("startDate") Date startDate,
			@Param("availableCreditLimit") Double availableCreditLimit, @Param("accountId") Integer accountId);

	@Modifying
	@Query(value = "update account_details set available_credit_limit=:availableCreditLimit where account_id=:accountId", nativeQuery = true)
	public void updateAccountDetails(@Param("availableCreditLimit") Double availableCreditLimit,
			@Param("accountId") Integer accountId);

	// after paymentDate
	@Query(value = "select AD.accountId as accountId,LI.processingDate as processingDate ,LI.interestAmount as interestAmount,"
			+ " LI.balanceAmount as balanceAmount,TD.transactionAmount as transactionAmount , TD.transactionDate as transactionDate , TD.transactionCode as transactionCode"
			+ ",TD.transactionType as transactionType, TD.transactoinDescription as transactoinDescription"
			+ "  from   TransactionDetails TD join  AccountDetails AD on AD.accountId = TD.accountDetails "
			+ "join LateInterest LI on AD.accountId = LI.accountDetails where AD.accountId=:accountId and TD.transactionCode=:transactionCode and  LI.processingDate<TD.transactionDate")
	public List<ITransactionCustomized> fetchAfterDueDateTransactionDetails(
			@Param("transactionCode") String transactionCode, @Param("accountId") Integer accountId);

}

//	@Query(value = "select AD.accountId as accountId from AccountDetails AD where AD.paymentStatus=:paymentStatus and AD.accountId not in (select AD.accountId from TransactionDetails TD join AccountDetails AD on AD.accountId = TD.accountDetails)")
//public List<ITransactionCustomized> fetchAccountDetails1(@Param("paymentStatus") char paymentStatus);
