package com.example.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.model.Admin;
import com.example.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AdminService {

	private final AdminRepository adminRepository;
	private final Logger logger = LoggerFactory.getLogger(AdminService.class);
	// Inject the dependencies using constructor
	// -------------------------------------------------------------------
	public AdminService(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

	// Implementation of the function to login for the admin
	// -------------------------------------------------------------------

	public ResponseEntity<String> loginAdminService(Admin loginRequest) {
		String username = loginRequest.getUsername();
		String password = loginRequest.getPassword();
		Optional<Admin> customerSupport = adminRepository.findByUsernameAndPassword(username, password);
		logger.info("Attempting to log in admin with username: {}", username);
		if (customerSupport.isPresent()) {
			return ResponseEntity.ok("Admin logged in successfully!");
		} else {
			logger.warn("Admin login failed for username: {}", username);
			return ResponseEntity.badRequest().body("Invalid username or password");
		}
	}

}
