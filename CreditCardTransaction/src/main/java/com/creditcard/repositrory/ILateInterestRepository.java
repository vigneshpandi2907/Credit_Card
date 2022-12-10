package com.creditcard.repositrory;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.creditcard.entity.LateInterest;

public interface ILateInterestRepository extends JpaRepository<LateInterest, Integer> {
	@Query(value = "select LI from LateInterest LI join AccountDetails AD on LI.accountDetails=AD.accountId  where AD.accountId=:accountID")
	public List<LateInterest> fetchLateInterestDetails(@Param("accountID") Integer accountID);

	@Modifying(clearAutomatically = true)
	@Query(value = "update late_interest set processing_date=:fetchCurrentDate,interest_amount=:interestAmount, balance_amount=:balanceAmount ,cash_amount =:cashAmount"
			+ " where late_interest_id=:lateInterestId", nativeQuery = true)
	public void updateLateInterest(@Param("fetchCurrentDate") Date fetchCurrentDate,
			@Param("interestAmount") Double interestAmount, @Param("balanceAmount") Double balanceAmount,
			@Param("cashAmount") Double cashAmount, @Param("lateInterestId") Integer lateInterestId);

}
