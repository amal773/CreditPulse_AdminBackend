package com.example.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.exception.ResourceNotFoundException;
import com.example.model.ApplicationStatus;
import com.example.model.CreditCard;
import com.example.model.CustomerCardAccount;
import com.example.model.CustomerProfile;
import com.example.model.GuestProfile;
import com.example.model.CustomerCardAccount.ActivationStatus;
import com.example.model.CustomerCardAccount.PaymentStatus;
import com.example.repository.CreditCardRepository;
import com.example.repository.CustomerCardAccountRepository;
import com.example.repository.CustomerProfileRepository;
import com.example.repository.GuestRepository;
import com.example.util.Hasher;
import com.example.util.KeyGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GuestService {
	private final Logger logger = LoggerFactory.getLogger(AdminService.class);
	private final GuestRepository guestRepository;

	private final CustomerProfileRepository customerProfileRepository;

	private final CustomerCardAccountRepository customerCardAccountRepository;

	private final CreditCardRepository creditCardRepository;

	@Autowired
	private JavaMailSender mailSender;

	private static final KeyGenerator generator = new KeyGenerator();

	// Inject the dependencies using constructor
	// -------------------------------------------------------------------

	public GuestService(GuestRepository guestRepository, CustomerProfileRepository customerProfileRepository,
			CustomerCardAccountRepository customerCardAccountRepository, CreditCardRepository creditCardRepository) {
		this.guestRepository = guestRepository;
		this.customerProfileRepository = customerProfileRepository;
		this.customerCardAccountRepository = customerCardAccountRepository;
		this.creditCardRepository = creditCardRepository;
	}

	// Implementation of the function to get the number of Guest Applications
	// -------------------------------------------------------------------

	public Integer getGuestApplicationCount() {

		return guestRepository.getGuestApplicationCount();
	}

	// Implementation of the function to get the number of Approved Guest
	// Applications
	// -------------------------------------------------------------------

	public Integer getGuestApplicationResolvedCount() {

		return guestRepository.getGuestApplicationResolvedCount();
	}

	// Implementation of the function to get the number of Pending Guest
	// Applications
	// -------------------------------------------------------------------

	public Integer getGuestApplicationPendingCount() {

		return guestRepository.getGuestApplicationPendingCount();
	}

	// Implementation of the function to get all the Guest Applications
	// -------------------------------------------------------------------

	public Page<GuestProfile> getAllGuestApplications(Pageable pageable) {
		return guestRepository.findAll(pageable);
	}

	// Implementation of the function to get all the Pending Guest Applications
	// -------------------------------------------------------------------

	public Page<GuestProfile> getAllPendingGuestApplications(Pageable pageable) {
		return guestRepository.getAllPendingGuestApplications(pageable);
	}

	// Implementation of the function to approve Guest Applications
	// -------------------------------------------------------------------

	@Transactional
	public ResponseEntity<Object> approveGuestApplication(String email) throws ResourceNotFoundException {
		logger.info("Approving guest application with email: {}", email);
		Optional<GuestProfile> old = guestRepository.findById(email);

		if (old.isEmpty())
			throw new ResourceNotFoundException("Customer Application not found for the given emailID ");

		old.get().setApplicationStatus(ApplicationStatus.APPROVED);

		GuestProfile guest = guestRepository.save(old.get());

		String password = generator.generatePassword();

		CustomerProfile newCustomer = addNewCustomer(guest, password);

		int pin = addNewCard(newCustomer, guest.getCreditCard().getCardType());

//		guest.setApplicationStatus(ApplicationStatus.APPROVED);
		guestRepository.save(guest);

		sendEmail(old.get().getGuestEmail(), "Credit Card Application Status Update",
				"Congratulations! Your credit card application has been approved. Please find your login credentials below: \nUSERNAME : "
						+ newCustomer.getCustomerId() + "\n" + "PASSWORD :" + password + "\n" + "PIN :" + pin);
		logger.info("Guest application approved and email sent to: {}", old.get().getGuestEmail());
		return new ResponseEntity<>("Guest Application approved successfully", HttpStatus.OK);
	}

	// Implementation of the function to reject Guest Applications
	// -------------------------------------------------------------------

	@Transactional
	public ResponseEntity<Object> rejectGuestApplication(String email) throws ResourceNotFoundException {
		logger.info("Rejecting guest application with email: {}", email);
		Optional<GuestProfile> old = guestRepository.findById(email);

		if (old.isEmpty())
			throw new ResourceNotFoundException("Customer Application not found for the emailID : " + email);

		old.get().setApplicationStatus(ApplicationStatus.REJECTED);

		GuestProfile guest = guestRepository.save(old.get());

		sendEmail(guest.getGuestEmail(), "Credit Card Application Status Update",
				"Unfortunately, we are unable to verify your application for the credit card, and as a result, it has been rejected.");
		logger.info("Guest application rejected and email sent to: {}", guest.getGuestEmail());
		return new ResponseEntity<>("Guest Application rejected successfully", HttpStatus.OK);

	}

	// Function to create new Customer
	// -------------------------------------------------------------------

	public CustomerProfile addNewCustomer(GuestProfile guest, String password) throws ResourceNotFoundException {

		CustomerProfile newCustomer = new CustomerProfile();
		newCustomer.setName(guest.getName());
		newCustomer.setEmail(guest.getGuestEmail());
		newCustomer.setAadhaarNumber(guest.getAadhaarNumber());
		newCustomer.setAddress(guest.getAddress());
		newCustomer.setPanId(guest.getPanId());
		newCustomer.setMobileNumber(guest.getMobileNumber());
		newCustomer.setDob(guest.getDob());
		newCustomer.setFirstLogin(true);
		newCustomer.setEmploymentYears(guest.getEmploymentYears());

		newCustomer.setCompanyName(guest.getCompanyName());
		newCustomer.setAnnualIncome(guest.getAnnualIncome());
		newCustomer.setIncomeProofFilePath(guest.getIncomeProofFilePath());

		newCustomer.setPassword(Hasher.hashPassword(password));
		logger.info("New customer profile created for guest: {}", guest.getGuestEmail());

		return customerProfileRepository.save(newCustomer);
	}

	// Function to create new Credit Card
	// -------------------------------------------------------------------

	public int addNewCard(CustomerProfile newCustomer, String card) {
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
		newAccount.setCardBalance(new BigDecimal("1000"));
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
		newAccount.setCreditCardLimit(c.get().getMaxLimit());
		newAccount.setDueAmount(new BigDecimal("1000"));
		newAccount.setOpeningDate(now);
		newAccount.setExpiryDate(calendar.getTime());
		newAccount.setDueDate(now);
		newAccount.setInternationalPaymentLimit(BigDecimal.ZERO);
		newAccount.setOnlinePaymentLimit(new BigDecimal("20000"));
		logger.info("New card added for customer: {}", newCustomer.getCustomerId());
		return customerCardAccountRepository.save(newAccount).getPin();
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

}
