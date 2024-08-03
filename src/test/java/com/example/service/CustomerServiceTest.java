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

import com.example.dto.CustomerApplicationDTO;
import com.example.exception.ResourceNotFoundException;
import com.example.model.*;
import com.example.model.CustomerCardApplication.ApplicationStatus;
import com.example.repository.*;
import com.example.util.KeyGenerator;

public class CustomerServiceTest {

    @Mock
    private CustomerProfileRepository customerProfileRepository;

    @Mock
    private CreditCardRepository creditCardRepository;

    @Mock
    private CustomerCardApplicationRepository customerCardApplicationRepository;

    @Mock
    private CustomerCardAccountRepository customerCardAccountRepository;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private CustomerService customerService;

    private KeyGenerator generator = new KeyGenerator();

    private CustomerProfile customerProfile;
    private CustomerCardApplication customerCardApplication;
    private CustomerCardAccount customerCardAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customerProfile = new CustomerProfile();
        customerProfile.setCustomerId(1L);
        customerProfile.setEmail("customer@example.com");

        String creditCard = "Cred";

        customerCardApplication = new CustomerCardApplication();
        customerCardApplication.setApplicationId(1L);
        customerCardApplication.setCustomerProfile(customerProfile);
        customerCardApplication.setCreditCard(creditCard);
        customerCardApplication.setStatus(ApplicationStatus.PENDING);

        customerCardAccount = new CustomerCardAccount();
        customerCardAccount.setAccountNumber(1L);
        customerCardAccount.setCustomerProfile(customerProfile);
        customerCardAccount.setCreditCard("VISA");

        doNothing().when(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void testGetCustomerApplicationCount() {
        when(customerCardApplicationRepository.getCustomerApplicationCount()).thenReturn(10);
        Integer count = customerService.getCustomerApplicationCount();
        assertEquals(10, count);
    }

    @Test
    void testGetCustomerApplicationResolvedCount() {
        when(customerCardApplicationRepository.getCustomerApplicationResolvedCount()).thenReturn(5);
        Integer count = customerService.getCustomerApplicationResolvedCount();
        assertEquals(5, count);
    }

    @Test
    void testGetCustomerApplicationPendingCount() {
        when(customerCardApplicationRepository.getCustomerApplicationPendingCount()).thenReturn(3);
        Integer count = customerService.getCustomerApplicationPendingCount();
        assertEquals(3, count);
    }

    @Test
    void testGetAllCustomers() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CustomerProfile> page = new PageImpl<>(List.of(customerProfile));
        when(customerProfileRepository.findAll(pageable)).thenReturn(page);
        Page<CustomerProfile> result = customerService.getAllCustomers(pageable);
        assertEquals(page, result);
    }

    @Test
    void testGetAllCustomerCardDetails() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CustomerCardAccount> page = new PageImpl<>(List.of(customerCardAccount));
        when(customerCardAccountRepository.findAll(pageable)).thenReturn(page);
        Page<CustomerCardAccount> result = customerService.getAllCustomerCardDetails(pageable);
        assertEquals(page, result);
    }

    @Test
    void testGetAllCustomerApplications() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CustomerCardApplication> page = new PageImpl<>(List.of(customerCardApplication));
        when(customerCardApplicationRepository.findAll(pageable)).thenReturn(page);
        Page<CustomerCardApplication> result = customerService.getAllCustomerApplications(pageable);
        assertEquals(page, result);
    }

    @Test
    void testGetAllPendingCustomerApplications() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CustomerApplicationDTO> page = new PageImpl<>(List.of(new CustomerApplicationDTO(null, null, null, null, null, null, null, null, null, null, null, null)));
        when(customerCardApplicationRepository.getAllPendingCustomerApplications(pageable)).thenReturn(page);
        Page<CustomerApplicationDTO> result = customerService.getAllPendingCustomerApplications(pageable);
        assertEquals(page, result);
    }

    @Test
    void testGetAllPendingCustomerUpgradeApplications() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CustomerApplicationDTO> page = new PageImpl<>(List.of(new CustomerApplicationDTO(null, null, null, null, null, null, null, null, null, null, null, null)));
        when(customerCardApplicationRepository.getAllPendingCustomerUpgradeApplications(pageable)).thenReturn(page);
        Page<CustomerApplicationDTO> result = customerService.getAllPendingCustomerUpgradeApplications(pageable);
        assertEquals(page, result);
    }

    @Test
    void testApproveCustomerApplication() throws ResourceNotFoundException {
        when(customerCardApplicationRepository.findById(1L)).thenReturn(Optional.of(customerCardApplication));
        when(customerProfileRepository.findById(1L)).thenReturn(Optional.of(customerProfile));
        when(creditCardRepository.findById("VISA")).thenReturn(Optional.of(new CreditCard()));

       
    }

    @Test
    void testApproveCustomerApplication_NotFound() {
        when(customerCardApplicationRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> customerService.approveCustomerApplication(1L));
    }

    @Test
    void testRejectCustomerApplication() throws ResourceNotFoundException {
        when(customerCardApplicationRepository.findById(1L)).thenReturn(Optional.of(customerCardApplication));
        when(customerProfileRepository.findById(1L)).thenReturn(Optional.of(customerProfile));

    }

    @Test
    void testRejectCustomerApplication_NotFound() {
        when(customerCardApplicationRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> customerService.rejectCustomerApplication(1L));
    }

    @Test
    void testApproveCustomerUserApplication() throws ResourceNotFoundException {
        when(customerCardApplicationRepository.findById(1L)).thenReturn(Optional.of(customerCardApplication));
        when(customerCardAccountRepository.findById(1L)).thenReturn(Optional.of(customerCardAccount));
        when(customerProfileRepository.findById(1L)).thenReturn(Optional.of(customerProfile));


    }

    @Test
    void testApproveCustomerUserApplication_NotFound() {
        when(customerCardApplicationRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> customerService.approveCustomerUserApplication(1L));
    }

    @Test
    void testAddNewCard() {
        CreditCard creditCard = new CreditCard();
        creditCard.setMaxLimit(new BigDecimal("100000"));
        when(creditCardRepository.findById("VISA")).thenReturn(Optional.of(creditCard));

        customerService.addNewCard(customerProfile, "VISA");

        verify(customerCardAccountRepository, times(1)).save(any(CustomerCardAccount.class));
    }
}
