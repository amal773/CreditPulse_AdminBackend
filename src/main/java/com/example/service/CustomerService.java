package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.CustomerApplicationDTO;
import com.example.exception.ResourceNotFoundException;
import com.example.model.CreditCard;
import com.example.model.CustomerCardAccount;
import com.example.model.CustomerCardApplication;
import com.example.model.CustomerProfile;
import com.example.model.CustomerCardAccount.ActivationStatus;
import com.example.model.CustomerCardAccount.PaymentStatus;
import com.example.model.CustomerCardApplication.ApplicationStatus;
import com.example.repository.CreditCardRepository;
import com.example.repository.CustomerCardAccountRepository;
import com.example.repository.CustomerCardApplicationRepository;
import com.example.repository.CustomerProfileRepository;
import com.example.util.KeyGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class CustomerService {
	
	
	private final Logger logger = LoggerFactory.getLogger(AdminService.class);
	private final CustomerProfileRepository customerProfileRepository;

	private final CreditCardRepository creditCardRepository;

	private final CustomerCardApplicationRepository customerCardApplicationRepository;

	private final CustomerCardAccountRepository customerCardAccountRepository;

	private static final String CUSTOMER_MESSAGE = "Customer Application not found for the id : ";

	private static final String NO_CUSTOMER_MESSAGE = "No customer present with the given id";

	@Autowired
	private JavaMailSender mailSender;

	private static final KeyGenerator generator = new KeyGenerator();

	// Inject the dependencies using constructor
	// -------------------------------------------------------------------

	public CustomerService(CustomerProfileRepository customerProfileRepository,
			CreditCardRepository creditCardRepository,
			CustomerCardApplicationRepository customerCardApplicationRepository,
			CustomerCardAccountRepository customerCardAccountRepository) {
		this.customerProfileRepository = customerProfileRepository;
		this.creditCardRepository = creditCardRepository;
		this.customerCardApplicationRepository = customerCardApplicationRepository;
		this.customerCardAccountRepository = customerCardAccountRepository;
	}

	// Implementation of the function to get the number of Customer Applications
	// -------------------------------------------------------------------
	public Integer getCustomerApplicationCount() {

		return customerCardApplicationRepository.getCustomerApplicationCount();
	}

	// Implementation of the function to get the number of Resolved Customer
	// Applications
	// -------------------------------------------------------------------

	public Integer getCustomerApplicationResolvedCount() {

		return customerCardApplicationRepository.getCustomerApplicationResolvedCount();
	}

	// Implementation of the function to get the number of Pending Customer
	// Applications
	// -------------------------------------------------------------------

	public Integer getCustomerApplicationPendingCount() {

		return customerCardApplicationRepository.getCustomerApplicationPendingCount();
	}

	// Implementation of the function to get all the Customers
	// -------------------------------------------------------------------

	public Page<CustomerProfile> getAllCustomers(Pageable pageable) {
		return customerProfileRepository.findAll(pageable);
	}

	// Implementation of the function to get all the Issued Credit Cards
	// -------------------------------------------------------------------

	public Page<CustomerCardAccount> getAllCustomerCardDetails(Pageable pageable) {
		return customerCardAccountRepository.findAll(pageable);
	}

	// Implementation of the function to get all the Customer Applications
	// -------------------------------------------------------------------

	public Page<CustomerCardApplication> getAllCustomerApplications(Pageable pageable) {
		return customerCardApplicationRepository.findAll(pageable);
	}

	// Implementation of the function to get all the Pending Customer Applications
	// for new credit card
	// -------------------------------------------------------------------

	public Page<CustomerApplicationDTO> getAllPendingCustomerApplications(Pageable pageable) {
		return customerCardApplicationRepository.getAllPendingCustomerApplications(pageable);
	}

	// Implementation of the function to get all the Pending Customer Applications
	// for upgrading existing credit card
	// -------------------------------------------------------------------

	public Page<CustomerApplicationDTO> getAllPendingCustomerUpgradeApplications(Pageable pageable) {
		return customerCardApplicationRepository.getAllPendingCustomerUpgradeApplications(pageable);
	}

	// Implementation of the function to approve Customer Applications for new
	// credit card
	// -------------------------------------------------------------------

	@Transactional
	public ResponseEntity<Object> approveCustomerApplication(Long applicationId) throws ResourceNotFoundException {
		Optional<CustomerCardApplication> old = customerCardApplicationRepository.findById(applicationId);
		if (old.isEmpty())
			throw new ResourceNotFoundException(CUSTOMER_MESSAGE + applicationId);

		old.get().setStatus(ApplicationStatus.APPROVED);

		Optional<CustomerProfile> cust = customerProfileRepository
				.findById(old.get().getCustomerProfile().getCustomerId());

		if (cust.isPresent()) {
			addNewCard(cust.get(), old.get().getCreditCard());
			sendEmail(cust.get().getEmail(), "Credit Card Application Status Update",
					"Congratulations! Your credit card application has been approved.");
			logger.info("Customer application approved and email sent to: {}", cust.get().getEmail());
		} else {
			throw new ResourceNotFoundException(NO_CUSTOMER_MESSAGE);
		}

		return new ResponseEntity<>("Customer Application approved successfully", HttpStatus.OK);

	}

	// Implementation of the function to reject Customer Applications for new credit
	// card
	// -------------------------------------------------------------------

	@Transactional
	public ResponseEntity<Object> rejectCustomerApplication(Long applicationId) throws ResourceNotFoundException {
		logger.info("Rejecting customer application with ID: {}", applicationId);
		Optional<CustomerCardApplication> old = customerCardApplicationRepository.findById(applicationId);

		if (old.isEmpty())
			throw new ResourceNotFoundException(CUSTOMER_MESSAGE + applicationId);

		old.get().setStatus(ApplicationStatus.REJECTED);

		customerCardApplicationRepository.save(old.get());

		Optional<CustomerProfile> cust = customerProfileRepository
				.findById(old.get().getCustomerProfile().getCustomerId());

		if (cust.isPresent()) {
			sendEmail(cust.get().getEmail(), "Credit Card Application Status Update",
					"Unfortunately, we are unable to verify your application for the credit card, and as a result, it has been rejected.");
			logger.info("Customer application rejected and email sent to: {}", cust.get().getEmail());
		} else {
			throw new ResourceNotFoundException(NO_CUSTOMER_MESSAGE);
		}

		return new ResponseEntity<>("Customer Application rejected successfully", HttpStatus.OK);

	}

	// Implementation of the function to approve Customer Applications to upgrade
	// existing credit card
	// -------------------------------------------------------------------

	@Transactional
	public ResponseEntity<Object> approveCustomerUserApplication(Long applicationId) throws ResourceNotFoundException {
	
		logger.info("Approving customer application with ID: {}", applicationId);
		Optional<CustomerCardApplication> old = customerCardApplicationRepository.findById(applicationId);
		if (old.isEmpty())
			throw new ResourceNotFoundException(CUSTOMER_MESSAGE + applicationId);

		old.get().setStatus(ApplicationStatus.APPROVED);

		Optional<CustomerCardAccount> customerAccount = customerCardAccountRepository
				.findById(old.get().getAccountNumber());
		
		Optional<CustomerProfile> cust = customerProfileRepository
				.findById(old.get().getCustomerProfile().getCustomerId());

		if (customerAccount.isPresent()) {
			customerAccount.get().setCreditCard(old.get().getCreditCard());  
			sendEmail(cust.get().getEmail(), "Credit Card Upgradation Application Status Update",
					"Unfortunately, we are unable to verify your application for the credit card, and as a result, it has been rejected.");
			
			logger.info("Customer user application approved and email sent to: {}", cust.get().getEmail());
		} else {
			throw new ResourceNotFoundException(NO_CUSTOMER_MESSAGE);
		}
		
		return new ResponseEntity<>("Customer Application approved successfully", HttpStatus.OK);

	}

	// Function to send email
	// -------------------------------------------------------------------

	public void sendEmail(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		
		mailSender.send(message);
		logger.info("Email sent to: {} with subject: {}", to, subject);
	}

	// Function to add new credit card
	// -------------------------------------------------------------------
	
	public void addNewCard(CustomerProfile newCustomer, String card) {
		Date now = new Date();

		// Create a Calendar instance
		Calendar calendar = Calendar.getInstance();

		// Set the calendar time to the current date
		calendar.setTime(now);

		// Add 5 years to the current date
		calendar.add(Calendar.YEAR, 5);
		CustomerCardAccount newAccount = new CustomerCardAccount();
		
		Optional<CreditCard> c = creditCardRepository.findById(card);
		
		newAccount.setCustomerProfile(newCustomer);
		newAccount.setCardStatus(ActivationStatus.ACTIVE);
		newAccount.setCardBalance(new BigDecimal("0"));
		newAccount.setActivationStatus(ActivationStatus.ACTIVE);
		newAccount.setInternationalPayment(PaymentStatus.DISABLE);
		newAccount.setOnlinePayment(PaymentStatus.ENABLE);
		newAccount.setCardSwipe(PaymentStatus.ENABLE);
		newAccount.setPin(generator.generatePin());
		newAccount.setCardNumber(generator.generateUniqueCardNumber());
		newAccount.setCvv(generator.generateCvv());
		newAccount.setCreditCard(card);
		newAccount.setBaseCurrency("INR");
		newAccount.setCardSwipeLimit(c.get().getMaxLimit());
		newAccount.setCreditCardLimit(c.get().getMaxLimit()
				);
		newAccount.setDueAmount(new BigDecimal("1000"));
		newAccount.setOpeningDate(now);
		newAccount.setExpiryDate(calendar.getTime());
		newAccount.setDueDate(now);
		newAccount.setInternationalPaymentLimit(BigDecimal.ZERO);
		newAccount.setOnlinePaymentLimit(new BigDecimal("20000"));

		customerCardAccountRepository.save(newAccount);
		logger.info("New card added for customer: {}", newCustomer.getCustomerId());
	}

}
