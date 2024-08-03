package com.example.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CustomerApplicationDTO;
import com.example.exception.BadRequestException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Admin;

import com.example.model.CustomerCardAccount;
import com.example.model.CustomerCardApplication;
import com.example.model.CustomerProfile;
import com.example.model.GuestProfile;
import com.example.service.AdminService;
import com.example.service.CustomerService;
import com.example.service.GuestService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

	private CustomerService customerService;

	private GuestService guestService;

	private AdminService adminService;

	private static final String EMAIL_MESSAGE = "Email cannot be empty";
	private static final String ID_MESSAGE = "ID cannot be null";

	// Inject the dependencies using constructor
	// -------------------------------------------------------------------

	public AdminController(CustomerService customerService, GuestService guestService, AdminService adminService) {
		this.customerService = customerService;
		this.guestService = guestService;
		this.adminService = adminService;
	}

	// Login
	// --------------------------------------------------------------------------------------------------------

	@PostMapping("/login")
	public ResponseEntity<String> loginAdminService(@RequestBody Admin loginRequest) throws BadRequestException {
		if (loginRequest == null) {
			throw new BadRequestException("Username or password cannot be empty");
		}
		return adminService.loginAdminService(loginRequest);
	}

	// Get the count of total customer applications
	// ---------------------------------------------------------------

	@GetMapping("customerapplication/getcount")
	public Integer getCustomerApplicationCount() {

		return customerService.getCustomerApplicationCount();
	}

	// Get the count of total customer applications that were approved
	// --------------------------------------------

	@GetMapping("customerapplication/approvedgetcount")
	public Integer getCustomerApplicationResolvedCount() {

		return customerService.getCustomerApplicationResolvedCount();
	}

	// Get the count of total customer applications that are pending
	// ----------------------------------------------

	@GetMapping("customerapplication/pendinggetcount")
	public Integer getCustomerApplicationPendingCount() {

		return customerService.getCustomerApplicationPendingCount();
	}

	// Get the count of total guest applications
	// ------------------------------------------------------------------

	@GetMapping("guestapplication/getcount")
	public Integer getGuestApplicationCount() {

		return guestService.getGuestApplicationCount();
	}

	// Get the count of total guest applications that were approved
	// -----------------------------------------------
	@GetMapping("guestapplication/pendinggetcount")
	public Integer getGuestApplicationPendingCount() {

		return guestService.getGuestApplicationPendingCount();
	}

	// Get the count of total guest applications that are pending
	// -------------------------------------------------

	@GetMapping("guestapplication/approvedgetcount")
	public Integer getGuestApplicationResolvedCount() {

		return guestService.getGuestApplicationResolvedCount();
	}

	// Get all the pending guest applications
	// -------------------------------------------------

	@GetMapping("/guestapplication/readallpending")
	@Cacheable(value = "guestPages", key = "#page")
	public Page<GuestProfile> getAllPendingGuestApplications(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "9") int size) {

		Pageable pageable = PageRequest.of(page, size);
		return guestService.getAllPendingGuestApplications(pageable);
	}

	// Approve guest applications
	// -------------------------------------------------

	@PutMapping("/guestapplication/approve/{email}")
	public ResponseEntity<Object> approveGuestApplication(@PathVariable("email") String email)
			throws ResourceNotFoundException, BadRequestException {
		if (email == null || email.isEmpty()) {
			throw new BadRequestException(EMAIL_MESSAGE);
		}
		return guestService.approveGuestApplication(email);
	}

	// Reject guest applications
	// -------------------------------------------------
	@PutMapping("/guestapplication/reject/{email}")
	public ResponseEntity<Object> rejectGuestApplication(@PathVariable("email") String email)
			throws ResourceNotFoundException, BadRequestException {
		if (email == null || email.isEmpty()) {
			throw new BadRequestException(EMAIL_MESSAGE);
		}
		return guestService.rejectGuestApplication(email);
	}

	// Get all the customer applications for new credit card
	// -------------------------------------------------

	@GetMapping("/customerapplication/readall")
	@Cacheable(value = "customerPages", key = "#page")
	public Page<CustomerCardApplication> getAllCustomerApplications(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "9") int size) {

		Pageable pageable = PageRequest.of(page, size);
		return customerService.getAllCustomerApplications(pageable);
	}

	// Get all the pending customer applications for new credit card
	// -------------------------------------------------

	@GetMapping("/customerapplication/readallpending")
	@Cacheable(value = "customerPages", key = "#page")
	public Page<CustomerApplicationDTO> getAllPendingCustomerApplications(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "9") int size) {

		Pageable pageable = PageRequest.of(page, size);
		return customerService.getAllPendingCustomerApplications(pageable);
	}

	// Approve customer applications for new credit card
	// -------------------------------------------------

	@PutMapping("/customerapplication/approve/{id}")
	public ResponseEntity<Object> approveCustomerApplication(@PathVariable("id") Long id)
			throws ResourceNotFoundException, BadRequestException {
		if (id == null) {
			throw new BadRequestException(ID_MESSAGE);
		}
		return customerService.approveCustomerApplication(id);
	}

	// Reject customer applications for new credit card and upgrade existing credit
	// card
	// -------------------------------------------------

	@PutMapping("/customerapplication/reject/{id}")
	public ResponseEntity<Object> rejectCustomerApplication(@PathVariable("id") Long id)
			throws ResourceNotFoundException, BadRequestException {

		if (id == null) {
			throw new BadRequestException(ID_MESSAGE);
		}
		return customerService.rejectCustomerApplication(id);
	}

	// Get all the pending customer applications to upgrade existing credit card
	// -------------------------------------------------

	@GetMapping("/customerupgradeapplication/readallpending")
	@Cacheable(value = "customerPages", key = "#page")
	public Page<CustomerApplicationDTO> getAllPendingCustomerUpgradeApplications(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "9") int size) {

		Pageable pageable = PageRequest.of(page, size);
		return customerService.getAllPendingCustomerUpgradeApplications(pageable);
	}

	// Approve customer applications for upgrading existing credit card
	// -------------------------------------------------

	@PutMapping("/customerupgradeapplication/approve/{id}")
	public ResponseEntity<Object> approveCustomerUserApplication(@PathVariable("id") Long id)
			throws ResourceNotFoundException, BadRequestException {
		if (id == null) {
			throw new BadRequestException(ID_MESSAGE);
		}
		return customerService.approveCustomerUserApplication(id);
	}

	// Get all the customers
	// ----------------------------------------------------------------------------------------------

	@GetMapping("customer/readall")
	@Cacheable(value = "guestPages", key = "#page")
	public Page<CustomerProfile> getAllCustomers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "9") int size) {

		Pageable pageable = PageRequest.of(page, size);
		return customerService.getAllCustomers(pageable);
	}

	// Get all the cards issued
	// ----------------------------------------------------------------------------------------------

	@GetMapping("/customercarddetails/readall")
	@Cacheable(value = "customerPages", key = "#page")
	public Page<CustomerCardAccount> getAllCustomerCardDetails(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "9") int size) {

		Pageable pageable = PageRequest.of(page, size);
		return customerService.getAllCustomerCardDetails(pageable);
	}

	// Get all the guest applications
	// ----------------------------------------------------------------------------------------------

	@GetMapping("/guestapplication/readall")
	@Cacheable(value = "guestPages", key = "#page")
	public Page<GuestProfile> getAllGuestApplications(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "9") int size) {

		Pageable pageable = PageRequest.of(page, size);
		return guestService.getAllGuestApplications(pageable);
	}

}
