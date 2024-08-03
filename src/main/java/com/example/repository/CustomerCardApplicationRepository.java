package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.dto.CustomerApplicationDTO;
import com.example.model.CustomerCardApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface CustomerCardApplicationRepository extends JpaRepository<CustomerCardApplication, Long> {

	// Custom mysql query to get the number of approved customer applications
	// ------------------------------------------------------------------------
	@Query(value = "select count(*) from customer_card_application where application_status = 'APPROVED'", nativeQuery = true)
	Integer getCustomerApplicationResolvedCount();

	// Custom mysql query to get the number of pending customer applications
	// ------------------------------------------------------------------------
	@Query(value = "select count(*) from customer_card_application where application_status = 'PENDING'", nativeQuery = true)
	Integer getCustomerApplicationPendingCount();

	// Custom jpql query to get all the pending customer applications for new credit
	// card
	// ------------------------------------------------------------------------

	@Query("SELECT new com.example.dto.CustomerApplicationDTO(" + "c.applicationId, " + "p.customerId, "
			+ "p.name AS customerName, " + "p.email, " + "c.status, " + "p.annualIncome, " + "p.panId, "
			+ "p.mobileNumber, " + "p.companyName, " + "p.incomeProofFilePath, " + "p.name, " + "c.creditCard " + ") "
			+ "FROM CustomerCardApplication c " + "JOIN c.customerProfile p "
			+ "WHERE c.status = com.example.model.CustomerCardApplication$ApplicationStatus.PENDING "
			+ "AND c.isUpgrade = false")
	Page<CustomerApplicationDTO> getAllPendingCustomerApplications(Pageable pageable);

	// Custom jpql query to get all the pending customer applications for upgrading
	// current credit card
	// ------------------------------------------------------------------------

	@Query("SELECT new com.example.dto.CustomerApplicationDTO(" + "c.applicationId, " + "p.customerId, "
			+ "p.name AS customerName, " + "p.email, " + "c.status, " + "p.annualIncome, " + "p.panId, "
			+ "p.mobileNumber, " + "p.companyName, " + "p.incomeProofFilePath, " + "p.name, " + "c.creditCard" + ") "
			+ "FROM CustomerCardApplication c " + "JOIN c.customerProfile p "
			+ "WHERE c.status = com.example.model.CustomerCardApplication$ApplicationStatus.PENDING "
			+ "AND c.isUpgrade = true")
	Page<CustomerApplicationDTO> getAllPendingCustomerUpgradeApplications(Pageable pageable);

	// Custom mysql query to select all customer applications
	// ------------------------------------------------------------------------

	@Query(value = "select count(*) from customer_card_application", nativeQuery = true)
	Integer getCustomerApplicationCount();

}
