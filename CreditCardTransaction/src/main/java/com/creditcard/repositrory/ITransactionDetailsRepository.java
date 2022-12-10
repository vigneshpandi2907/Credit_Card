package com.creditcard.repositrory;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.creditcard.entity.TransactionDetails;

public interface ITransactionDetailsRepository extends JpaRepository<TransactionDetails, Integer> {

	@Query(value = "select sum(TD.transactionAmount) as transactionAmount from TransactionDetails TD join AccountDetails AD on TD.accountDetails=AD.accountId "
			+ "where TD.transactionDate BETWEEN :openingDate and :statementDate and TD.transactionCode='DE'and TD.transactionType!='cash' and AD.accountId=:accountId")
	public Double fetchTransactionAmount(@Param("openingDate") Date openingDate,
			@Param("statementDate") Date statementDate, @Param("accountId") Integer accountId);

	@Query(value = "select TD from TransactionDetails TD join AccountDetails AD on TD.accountDetails=AD.accountId "
			+ "where TD.transactionDate BETWEEN :statementDate and :paymentDate and TD.transactionCode=:transactionCode and AD.accountId=:accountId")
	public List<TransactionDetails> fetchTransactionDetails(@Param("statementDate") Date statementDate,
			@Param("paymentDate") Date paymentDate, @Param("transactionCode") String transactionCode,
			@Param("accountId") Integer accountId);

	@Query(value = "select TD as transactionAmount  from TransactionDetails TD join AccountDetails AD on TD.accountDetails=AD.accountId "
			+ "where TD.transactionDate BETWEEN :openingDate and :statementDate and TD.transactionCode=:transactionCode and TD.transactionType='cash' and AD.accountId=:accountId ")
	public List<TransactionDetails> fetchTransactionDetailsList(@Param("openingDate") Date openingDate,
			@Param("statementDate") Date statementDate, @Param("transactionCode") String transactionCode,
			@Param("accountId") Integer accountId);

	@Modifying
	@Query(value = "update late_interest set cashAmount=:cashAmount where account_id=:accountId", nativeQuery = true)
	public void updateCashAmount(@Param("cashAmount") Double cashAmount, @Param("accountId") Integer accountId);
}
