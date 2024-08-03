package com.example.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.model.CustomerCardApplication.ApplicationStatus;

class CustomerCardApplicationTests {

    private CustomerCardApplication application;
    private CustomerProfile mockProfile;
    private Admin mockAdmin;

    @BeforeEach
    void setUp() {
        mockProfile = mock(CustomerProfile.class);
        mockAdmin = mock(Admin.class);
        application = new CustomerCardApplication(
            123456789012L, 
            mockProfile, 
            123456789012L, 
            ApplicationStatus.PENDING, 
            "1234-5678-9101-1121", 
            987654321012L, 
            true, 
            mockAdmin
        );
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(123456789012L, application.getApplicationId());
        assertEquals(mockProfile, application.getCustomerProfile());
        assertEquals(123456789012L, application.getAadhaarNumber());
        assertEquals(ApplicationStatus.PENDING, application.getStatus());
        assertEquals("1234-5678-9101-1121", application.getCreditCard());
        assertEquals(987654321012L, application.getAccountNumber());
        assertTrue(application.getIsUpgrade());
        assertEquals(mockAdmin, application.getAdmin());

        // Test setters
        application.setApplicationId(210987654321L);
        application.setCustomerProfile(null);
        application.setAadhaarNumber(210987654321L);
        application.setStatus(ApplicationStatus.APPROVED);
        application.setCreditCard("1121-1101-9876-5432");
        application.setAccountNumber(210987654322L);
        application.setIsUpgrade(false);
        application.setAdmin(null);

        assertEquals(210987654321L, application.getApplicationId());
        assertNull(application.getCustomerProfile());
        assertEquals(210987654321L, application.getAadhaarNumber());
        assertEquals(ApplicationStatus.APPROVED, application.getStatus());
        assertEquals("1121-1101-9876-5432", application.getCreditCard());
        assertEquals(210987654322L, application.getAccountNumber());
        assertFalse(application.getIsUpgrade());
        assertNull(application.getAdmin());
    }

    @Test
    void testConstructor() {
        CustomerCardApplication newApplication = new CustomerCardApplication();
        assertNull(newApplication.getCustomerProfile());
        assertNull(newApplication.getAdmin());
        assertNull(newApplication.getAadhaarNumber());
        assertNull(newApplication.getStatus());
        assertNull(newApplication.getCreditCard());
        assertNull(newApplication.getAccountNumber());
    }
}
