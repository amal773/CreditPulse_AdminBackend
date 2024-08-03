package com.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerProfileTest {

    private CustomerProfile customerProfile;
    private List<CustomerCardAccount> customerCardAccounts;

    @BeforeEach
    public void setUp() {
        customerCardAccounts = new ArrayList<>();
        customerProfile = new CustomerProfile(
            1000000001L,
            "John Doe",
            123456789012L,
            "john.doe@example.com",
            "password123",
            "123 Main St",
            "ABCDE1234F",
            "1234567890",
            new Date(946684800000L), // January 1, 2000
            true,
            5,
            "Example Company",
            new BigDecimal("50000"),
            "path/to/incomeProof",
            customerCardAccounts
        );
    }

    @Test
    public void testCustomerProfileConstructor() {
        assertEquals(1000000001L, customerProfile.getCustomerId());
        assertEquals("John Doe", customerProfile.getName());
        assertEquals(123456789012L, customerProfile.getAadhaarNumber());
        assertEquals("john.doe@example.com", customerProfile.getEmail());
        assertEquals("password123", customerProfile.getPassword());
        assertEquals("123 Main St", customerProfile.getAddress());
        assertEquals("ABCDE1234F", customerProfile.getPanId());
        assertEquals("1234567890", customerProfile.getMobileNumber());
        assertEquals(new Date(946684800000L), customerProfile.getDob());
        assertEquals(true, customerProfile.getFirstLogin());
        assertEquals(5, customerProfile.getEmploymentYears());
        assertEquals("Example Company", customerProfile.getCompanyName());
        assertEquals(new BigDecimal("50000"), customerProfile.getAnnualIncome());
        assertEquals("path/to/incomeProof", customerProfile.getIncomeProofFilePath());
        assertEquals(customerCardAccounts, customerProfile.getCustomerCardAccounts());
    }

    @Test
    public void testCustomerProfileDefaultConstructor() {
        CustomerProfile customerProfile = new CustomerProfile();
        assertNull(customerProfile.getCustomerId());
        assertNull(customerProfile.getName());
        assertNull(customerProfile.getAadhaarNumber());
        assertNull(customerProfile.getEmail());
        assertNull(customerProfile.getPassword());
        assertNull(customerProfile.getAddress());
        assertNull(customerProfile.getPanId());
        assertNull(customerProfile.getMobileNumber());
        assertNull(customerProfile.getDob());
        assertEquals(Boolean.TRUE, customerProfile.getFirstLogin());
        assertNull(customerProfile.getEmploymentYears());
        assertNull(customerProfile.getCompanyName());
        assertNull(customerProfile.getAnnualIncome());
        assertNull(customerProfile.getIncomeProofFilePath());
        assertNull(customerProfile.getCustomerCardAccounts());
    }

    @Test
    public void testSettersAndGetters() {
        CustomerProfile customerProfile = new CustomerProfile();

        customerProfile.setCustomerId(1000000001L);
        assertEquals(1000000001L, customerProfile.getCustomerId());

        customerProfile.setName("John Doe");
        assertEquals("John Doe", customerProfile.getName());

        customerProfile.setAadhaarNumber(123456789012L);
        assertEquals(123456789012L, customerProfile.getAadhaarNumber());

        customerProfile.setEmail("john.doe@example.com");
        assertEquals("john.doe@example.com", customerProfile.getEmail());

        customerProfile.setPassword("password123");
        assertEquals("password123", customerProfile.getPassword());

        customerProfile.setAddress("123 Main St");
        assertEquals("123 Main St", customerProfile.getAddress());

        customerProfile.setPanId("ABCDE1234F");
        assertEquals("ABCDE1234F", customerProfile.getPanId());

        customerProfile.setMobileNumber("1234567890");
        assertEquals("1234567890", customerProfile.getMobileNumber());

        Date dob = new Date(946684800000L); // January 1, 2000
        customerProfile.setDob(dob);
        assertEquals(dob, customerProfile.getDob());

        customerProfile.setFirstLogin(true);
        assertEquals(true, customerProfile.getFirstLogin());

        customerProfile.setEmploymentYears(5);
        assertEquals(5, customerProfile.getEmploymentYears());

        customerProfile.setCompanyName("Example Company");
        assertEquals("Example Company", customerProfile.getCompanyName());

        BigDecimal annualIncome = new BigDecimal("50000");
        customerProfile.setAnnualIncome(annualIncome);
        assertEquals(annualIncome, customerProfile.getAnnualIncome());

        customerProfile.setIncomeProofFilePath("path/to/incomeProof");
        assertEquals("path/to/incomeProof", customerProfile.getIncomeProofFilePath());

        customerProfile.setCustomerCardAccounts(customerCardAccounts);
        assertEquals(customerCardAccounts, customerProfile.getCustomerCardAccounts());
    }
}
