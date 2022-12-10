package com.creditcard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.creditcard.entity.LatePaymentCustomized;
import com.creditcard.entity.TransactionDeatailsCustomized;
import com.creditcard.entity.TransactionDetails;

@Repository

public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Integer> {

	/**
	 * fetch transactions for particular account,this is for the use of credit and
	 * debit calculation
	 * 
	 * @param accountId
	 * @return
	 */
	@Query(value = "SELECT  transaction_amount as transactionAmount ,transaction_id as transactionId,transaction_code as transactionCode FROM transaction_details td "
			+ "inner join account_details ac on(td.account_id=ac.account_id) "
			+ "where transaction_date between  (select  top 1 statement_generated_date from"
			+ " statemet_details sd inner join account_details ac on(sd.account_id = ac.account_id)  "
			+ "where ac.account_id = :accountId ORDER BY sd.statemet_id desc)   and ac.statement_date "
			+ "and ac.account_id=:accountId", nativeQuery = true)
	public List<TransactionDeatailsCustomized> fetchTransactions(@Param("accountId") Integer accountId);

	/**
	 * fetch transaction for particular account who have late payment charges
	 * 
	 * @param accountId
	 * @return
	 */
	@Query(value = "SELECT transaction_amount as transactionAmount ,"
			+ "transaction_id as transactionId,transaction_code as transactionCode "
			+ "FROM transaction_details td inner join account_details ac"
			+ " on(td.account_id=ac.account_id) 	where transaction_date between "
			+ " (select  top 1 statement_generated_date from  statemet_details sd "
			+ "inner join account_details ac on(sd.account_id = ac.account_id) "
			+ " where ac.account_id =:accountId ORDER BY sd.statemet_id desc)   and ac.payment_date "
			+ "and ac.account_id=:accountId", nativeQuery = true)
	public List<LatePaymentCustomized> fetchLatePaymentTransaction(@Param("accountId") Integer accountId);

}
