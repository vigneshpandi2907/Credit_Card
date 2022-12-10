package com.creditcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.creditcard.entity.LatePaymentCharge;

@Repository
public interface LatePaymentChargeRepository extends JpaRepository<LatePaymentCharge, Integer> {
	@Query(value = "  select top 1 charges from late_payment_charge where (minimum_balance>=:fee and maximum_balance>:fee )", nativeQuery = true)
	public Double fetchLatePaymentCharge(@Param("fee") Double fee);

}
