package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import java.util.Collections;

public class AdminControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private GuestService guestService;

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginAdminService() throws BadRequestException {
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("password");

        when(adminService.loginAdminService(any(Admin.class))).thenReturn(new ResponseEntity<>("Login successful", HttpStatus.OK));

        ResponseEntity<String> response = adminController.loginAdminService(admin);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login successful", response.getBody());
    }

    @Test
    public void testLoginAdminService_BadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            adminController.loginAdminService(null);
        });
    }

    @Test
    public void testGetCustomerApplicationCount() {
        when(customerService.getCustomerApplicationCount()).thenReturn(10);
        Integer count = adminController.getCustomerApplicationCount();
        assertEquals(10, count);
    }

    @Test
    public void testGetCustomerApplicationResolvedCount() {
        when(customerService.getCustomerApplicationResolvedCount()).thenReturn(5);
        Integer count = adminController.getCustomerApplicationResolvedCount();
        assertEquals(5, count);
    }

    @Test
    public void testGetCustomerApplicationPendingCount() {
        when(customerService.getCustomerApplicationPendingCount()).thenReturn(3);
        Integer count = adminController.getCustomerApplicationPendingCount();
        assertEquals(3, count);
    }

    @Test
    public void testGetAllCustomers() {
        Pageable pageable = PageRequest.of(0, 9);
        Page<CustomerProfile> page = new PageImpl<>(Collections.singletonList(new CustomerProfile()));
        when(customerService.getAllCustomers(pageable)).thenReturn(page);

        Page<CustomerProfile> result = adminController.getAllCustomers(0, 9);
        assertEquals(page, result);
    }

    @Test
    public void testGetAllCustomerCardDetails() {
        Pageable pageable = PageRequest.of(0, 9);
        Page<CustomerCardAccount> page = new PageImpl<>(Collections.singletonList(new CustomerCardAccount()));
        when(customerService.getAllCustomerCardDetails(pageable)).thenReturn(page);

        Page<CustomerCardAccount> result = adminController.getAllCustomerCardDetails(0, 9);
        assertEquals(page, result);
    }

    @Test
    public void testGetGuestApplicationCount() {
        when(guestService.getGuestApplicationCount()).thenReturn(10);
        Integer count = adminController.getGuestApplicationCount();
        assertEquals(10, count);
    }

    @Test
    public void testGetGuestApplicationResolvedCount() {
        when(guestService.getGuestApplicationResolvedCount()).thenReturn(5);
        Integer count = adminController.getGuestApplicationResolvedCount();
        assertEquals(5, count);
    }

    @Test
    public void testGetGuestApplicationPendingCount() {
        when(guestService.getGuestApplicationPendingCount()).thenReturn(3);
        Integer count = adminController.getGuestApplicationPendingCount();
        assertEquals(3, count);
    }

    @Test
    public void testGetAllGuestApplications() {
        Pageable pageable = PageRequest.of(0, 9);
        Page<GuestProfile> page = new PageImpl<>(Collections.singletonList(new GuestProfile()));
        when(guestService.getAllGuestApplications(pageable)).thenReturn(page);

        Page<GuestProfile> result = adminController.getAllGuestApplications(0, 9);
        assertEquals(page, result);
    }

    @Test
    public void testGetAllPendingGuestApplications() {
        Pageable pageable = PageRequest.of(0, 9);
        Page<GuestProfile> page = new PageImpl<>(Collections.singletonList(new GuestProfile()));
        when(guestService.getAllPendingGuestApplications(pageable)).thenReturn(page);

        Page<GuestProfile> result = adminController.getAllPendingGuestApplications(0, 9);
        assertEquals(page, result);
    }

    @Test
    public void testApproveGuestApplication() throws ResourceNotFoundException, BadRequestException {
        when(guestService.approveGuestApplication(anyString())).thenReturn(new ResponseEntity<>("Approved", HttpStatus.OK));

        ResponseEntity<Object> response = adminController.approveGuestApplication("guest@example.com");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Approved", response.getBody());
    }

    @Test
    public void testApproveGuestApplication_BadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            adminController.approveGuestApplication(null);
        });
    }

    @Test
    public void testRejectGuestApplication() throws ResourceNotFoundException, BadRequestException {
        when(guestService.rejectGuestApplication(anyString())).thenReturn(new ResponseEntity<>("Rejected", HttpStatus.OK));

        ResponseEntity<Object> response = adminController.rejectGuestApplication("guest@example.com");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Rejected", response.getBody());
    }

    @Test
    public void testRejectGuestApplication_BadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            adminController.rejectGuestApplication(null);
        });
    }

    @Test
    public void testGetAllCustomerApplications() {
        Pageable pageable = PageRequest.of(0, 9);
        Page<CustomerCardApplication> page = new PageImpl<>(Collections.singletonList(new CustomerCardApplication()));
        when(customerService.getAllCustomerApplications(pageable)).thenReturn(page);

        Page<CustomerCardApplication> result = adminController.getAllCustomerApplications(0, 9);
        assertEquals(page, result);
    }

    @Test
    public void testGetAllPendingCustomerApplications() {
        Pageable pageable = PageRequest.of(0, 9);
        Page<CustomerApplicationDTO> page = new PageImpl<>(Collections.singletonList(new CustomerApplicationDTO(null, null, null, null, null, null, null, null, null, null, null, null)));
        when(customerService.getAllPendingCustomerApplications(pageable)).thenReturn(page);

        Page<CustomerApplicationDTO> result = adminController.getAllPendingCustomerApplications(0, 9);
        assertEquals(page, result);
    }

    @Test
    public void testApproveCustomerApplication() throws ResourceNotFoundException, BadRequestException {
        when(customerService.approveCustomerApplication(anyLong())).thenReturn(new ResponseEntity<>("Approved", HttpStatus.OK));

        ResponseEntity<Object> response = adminController.approveCustomerApplication(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Approved", response.getBody());
    }

    @Test
    public void testApproveCustomerApplication_BadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            adminController.approveCustomerApplication(null);
        });
    }

    @Test
    public void testRejectCustomerApplication() throws ResourceNotFoundException, BadRequestException {
        when(customerService.rejectCustomerApplication(anyLong())).thenReturn(new ResponseEntity<>("Rejected", HttpStatus.OK));

        ResponseEntity<Object> response = adminController.rejectCustomerApplication(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Rejected", response.getBody());
    }

    @Test
    public void testRejectCustomerApplication_BadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            adminController.rejectCustomerApplication(null);
        });
    }

    @Test
    public void testGetAllPendingCustomerUpgradeApplications() {
        Pageable pageable = PageRequest.of(0, 9);
        Page<CustomerApplicationDTO> page = new PageImpl<>(Collections.singletonList(new CustomerApplicationDTO(null, null, null, null, null, null, null, null, null, null, null, null)));
        when(customerService.getAllPendingCustomerUpgradeApplications(pageable)).thenReturn(page);

        Page<CustomerApplicationDTO> result = adminController.getAllPendingCustomerUpgradeApplications(0, 9);
        assertEquals(page, result);
    }

    @Test
    public void testApproveCustomerUserApplication() throws ResourceNotFoundException, BadRequestException {
        when(customerService.approveCustomerUserApplication(anyLong())).thenReturn(new ResponseEntity<>("Approved", HttpStatus.OK));

        ResponseEntity<Object> response = adminController.approveCustomerUserApplication(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Approved", response.getBody());
    }

    @Test
    public void testApproveCustomerUserApplication_BadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            adminController.approveCustomerUserApplication(null);
        });
    }
}
