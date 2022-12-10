package com.creditcard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.creditcard.entity.AccountDetails;
import com.creditcard.entity.IAccountDetailsCustomized;
import com.creditcard.entity.StatementDetailsCustomized;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Integer> {

	/**
	 * update account details for particular account
	 * 
	 * @param billAmount
	 * @param accountId
	 */
	@Transactional
	@Modifying
	@Query("UPDATE AccountDetails SET lastBillAmount =:billAmount WHERE accountId=:accountId")
	public void updateAccountDetails(@Param("billAmount") Double billAmount, @Param("accountId") Integer accountId);

	/**
	 * fetch account for statement generation
	 */
	@Query("select accountId as accountId,availableCreditLimit as availableCreditLimit,lastBillAmount as lastBillAmount from AccountDetails where DATEPART(DAY, statementDate) =DATEPART(DAY, CURRENT_TIMESTAMP)")
	public List<IAccountDetailsCustomized> filteredAccountDetails();

	/**
	 * fetch lastbill amount for particular account
	 * 
	 * @param accountId
	 */
	@Query("select lastBillAmount as lastBillAmount from AccountDetails where accountId=:accountId")
	public IAccountDetailsCustomized fetchLastBillAmount(@Param("accountId") Integer accountId);

	/**
	 * fetch statement details for statement generation
	 * 
	 * @param accountId
	 * @return
	 */
	@Query(value = "select customer_name as customerName,card_number as cardNumber,credit_limit as creditCardLimit,available_credit_limit as availablecreditLimit,"
			+ "statement_date as statementDate,Payment_date as paymentDate from customer_details cd "
			+ "inner join account_details ad on (cd.customer_id=ad.customer_id) where account_id=:accountId", nativeQuery = true)
	public StatementDetailsCustomized statementDetails(@Param("accountId") Integer accountId);

}
