package com.creditcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.creditcard.entity.StatementDetails;

@Repository
public interface StatementDetailsRepository extends JpaRepository<StatementDetails, Integer> {

}
