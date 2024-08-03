package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.example.exception.ResourceNotFoundException;
import com.example.model.*;
import com.example.repository.*;
import com.example.util.Hasher;
import com.example.util.KeyGenerator;

public class GuestServiceTest {

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private CustomerProfileRepository customerProfileRepository;

    @Mock
    private CustomerCardAccountRepository customerCardAccountRepository;

    @Mock
    private CreditCardRepository creditCardRepository;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private GuestService guestService;

    private KeyGenerator generator = new KeyGenerator();

    private GuestProfile guestProfile;
    private CustomerProfile customerProfile;
    private CreditCard creditCard;
    private CustomerCardAccount customerCardAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        guestProfile = new GuestProfile();
        guestProfile.setGuestEmail("guest@example.com");
        guestProfile.setName("Guest");
        guestProfile.setAadhaarNumber(123456789012L);
        guestProfile.setAddress("123 Street");
        guestProfile.setPanId("ABCDE1234F");
        guestProfile.setMobileNumber("9876543210");
        guestProfile.setDob(new Date());
        guestProfile.setEmploymentYears(5);
        guestProfile.setCompanyName("ABC Corp");
        guestProfile.setAnnualIncome(new BigDecimal("500000"));
        guestProfile.setIncomeProofFilePath("path/to/proof");
        guestProfile.setApplicationStatus(ApplicationStatus.PENDING);
        creditCard = new CreditCard();
        creditCard.setCardType("Gold");
        creditCard.setMaxLimit(new BigDecimal("100000"));
        guestProfile.setCreditCard(creditCard);

        customerProfile = new CustomerProfile();
        customerProfile.setCustomerId(1L);
        customerProfile.setEmail("customer@example.com");

        customerCardAccount = new CustomerCardAccount();
        customerCardAccount.setAccountNumber(1L);
        customerCardAccount.setCustomerProfile(customerProfile);
        customerCardAccount.setCreditCard("Gold");

        doNothing().when(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void testGetGuestApplicationCount() {
        when(guestRepository.getGuestApplicationCount()).thenReturn(10);
        Integer count = guestService.getGuestApplicationCount();
        assertEquals(10, count);
    }

    @Test
    void testGetGuestApplicationResolvedCount() {
        when(guestRepository.getGuestApplicationResolvedCount()).thenReturn(5);
        Integer count = guestService.getGuestApplicationResolvedCount();
        assertEquals(5, count);
    }

    @Test
    void testGetGuestApplicationPendingCount() {
        when(guestRepository.getGuestApplicationPendingCount()).thenReturn(3);
        Integer count = guestService.getGuestApplicationPendingCount();
        assertEquals(3, count);
    }

    @Test
    void testGetAllGuestApplications() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<GuestProfile> page = new PageImpl<>(List.of(guestProfile));
        when(guestRepository.findAll(pageable)).thenReturn(page);
        Page<GuestProfile> result = guestService.getAllGuestApplications(pageable);
        assertEquals(page, result);
    }

    @Test
    void testGetAllPendingGuestApplications() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<GuestProfile> page = new PageImpl<>(List.of(guestProfile));
        when(guestRepository.getAllPendingGuestApplications(pageable)).thenReturn(page);
        Page<GuestProfile> result = guestService.getAllPendingGuestApplications(pageable);
        assertEquals(page, result);
    }

    @Test
    void testApproveGuestApplication() throws ResourceNotFoundException {
        when(guestRepository.findById("guest@example.com")).thenReturn(Optional.of(guestProfile));
        when(customerProfileRepository.save(any(CustomerProfile.class))).thenReturn(customerProfile);
        when(creditCardRepository.findById("Gold")).thenReturn(Optional.of(creditCard));


    }

    @Test
    void testApproveGuestApplication_NotFound() {
        when(guestRepository.findById("guest@example.com")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> guestService.approveGuestApplication("guest@example.com"));
    }

    @Test
    void testRejectGuestApplication() throws ResourceNotFoundException {
        when(guestRepository.findById("guest@example.com")).thenReturn(Optional.of(guestProfile));

  
        
    }

    @Test
    void testRejectGuestApplication_NotFound() {
        when(guestRepository.findById("guest@example.com")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> guestService.rejectGuestApplication("guest@example.com"));
    }

    @Test
    void testAddNewCustomer() throws ResourceNotFoundException {
        when(customerProfileRepository.save(any(CustomerProfile.class))).thenReturn(customerProfile);
        CustomerProfile result = guestService.addNewCustomer(guestProfile, "password");
        assertEquals(customerProfile, result);
    }

    @Test
    void testAddNewCard() {
        when(creditCardRepository.findById("Gold")).thenReturn(Optional.of(creditCard));
      
    }

    @Test
    void testSendEmail() {
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));
    }
}
