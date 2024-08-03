package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.model.CreditCard;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, String> {

	// Automated Query to find if a credit card is present with the given card
	// number
	// ------------------------------------------------------------------------

	@Query(value = "select max(card_number) from customer_card_account", nativeQuery = true)
    Long findLastCreditCardNumber();
	
}
