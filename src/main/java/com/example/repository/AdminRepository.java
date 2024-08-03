package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

	// Custom mysql query to find if an admin is present with the given username and
	// password
	// ------------------------------------------------------------------------

	@Query(value = "select * from admin where username = ?1 and password = ?2", nativeQuery = true)
	Optional<Admin> findByUsernameAndPassword(String email, String password);
}
