package com.creditcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.creditcard.entity.CashAdvance;
import com.creditcard.entity.CashAdvanceChargesCustomized;

@Repository
public interface CashAdvanceRepository extends JpaRepository<CashAdvance, Integer> {
	/**
	 * fetch trhe sum of cash advance fee for particular account
	 * 
	 * @return
	 */
	@Query(value = "select sum(cash_advance_fee) as cashAdvanceFee, sum(interest_amount)as cashAdvanceInterest"
			+ " from cash_advance ca inner join transaction_details td"
			+ " on(ca.transaction_id=td.transaction_id) inner join account_details ac"
			+ " on(td.account_id=ac.account_id) where transaction_date between "
			+ " (select  top 1 statement_generated_date from statemet_details sd inner"
			+ " join account_details ac on(sd.account_id = ac.account_id)  "
			+ "where ac.account_id =:accountId ORDER BY sd.statemet_id desc)"
			+ " and ac.statement_date and ac.account_id=:accountId ", nativeQuery = true)
	public CashAdvanceChargesCustomized fetchCashAdvanceCharge(@Param("accountId") Integer accountId);
}
