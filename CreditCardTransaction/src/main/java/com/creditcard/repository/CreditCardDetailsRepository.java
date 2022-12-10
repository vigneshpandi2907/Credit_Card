package com.creditcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.creditcard.entity.CreditCardDetails;
import com.creditcard.entity.TaxPercentageCustomized;

@Repository
public interface CreditCardDetailsRepository extends JpaRepository<CreditCardDetails, Integer> {
	/**
	 * fetch tax precentage for tax calculation for particular account
	 * 
	 * @param accountId
	 * @return
	 */
	@Query("SELECT ad.accountId as accountId,cd.creditCardId as creditCardId,cd.taxPercentage as taxPercentage from AccountDetails ad join CreditCardDetails cd on ad.creditCardDetails=cd.creditCardId where ad.accountId=:accountId")
	public TaxPercentageCustomized fetchTaxPercentage(@Param("accountId") Integer accountId);

	@Query(value = "select  minimum_repayment_amount from account_details ad inner join credit_card_details cc on(ad.credit_card_id=cc.credit_card_id)where account_id=:accountId", nativeQuery = true)
	public Double fetchMinimumRepayment(@Param("accountId") Integer accountId);

}
