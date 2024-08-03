package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.model.CustomerCardAccount;

@Repository
public interface CustomerCardAccountRepository extends JpaRepository<CustomerCardAccount, Long> {

	// Automated Query to find if a credit card is present with the given card
	// number
	// ------------------------------------------------------------------------

	@Query("SELECT MAX(c.cardNumber) FROM CustomerCardAccount c")
    Long findLastCreditCardNumber();
}
