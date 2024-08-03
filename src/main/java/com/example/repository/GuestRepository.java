package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.model.GuestProfile;

@Repository
public interface GuestRepository extends JpaRepository<GuestProfile, String> {

	// Custom mysql query to get the number of approved guest applications
	// ------------------------------------------------------------------------

	@Query(value = "select count(*) from guest_profile where application_status = 'APPROVED'", nativeQuery = true)
	public Integer getGuestApplicationResolvedCount();

	// Custom mysql query to get the number of pending guest applications
	// ------------------------------------------------------------------------

	@Query(value = "select count(*) from guest_profile where application_status = 'PENDING'", nativeQuery = true)
	public Integer getGuestApplicationPendingCount();

	// Custom mysql query to get all the pending guest applications
	// ------------------------------------------------------------------------

	@Query("SELECT g FROM GuestProfile g WHERE g.applicationStatus = 'PENDING'")
	public Page<GuestProfile> getAllPendingGuestApplications(Pageable pageable);


	// Custom mysql query to get all the guest applications
	// ------------------------------------------------------------------------

	@Query(value = "select count(*) from guest_profile", nativeQuery = true)
	public Integer getGuestApplicationCount();

	public boolean existsByApplicationId(Long applicationId);

}
