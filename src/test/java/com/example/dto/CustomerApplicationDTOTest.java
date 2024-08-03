package com.example.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.model.CustomerCardApplication.ApplicationStatus;

public class CustomerApplicationDTOTest {

    private CustomerApplicationDTO customerApplicationDTO;

    @BeforeEach
    public void setUp() {
        customerApplicationDTO = new CustomerApplicationDTO(
            1L, 
            1001L, 
            "John Doe", 
            "john.doe@example.com", 
            ApplicationStatus.PENDING, 
            new BigDecimal("50000"), 
            "ABCDE1234F", 
            "9876543210", 
            "ABC Corp", 
            "path/to/income/proof", 
            "John", 
            "Gold"
        );
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(1L, customerApplicationDTO.getApplicationId());
        assertEquals(1001L, customerApplicationDTO.getCustomerId());
        assertEquals("John Doe", customerApplicationDTO.getCustomerName());
        assertEquals("john.doe@example.com", customerApplicationDTO.getEmail());
        assertEquals(ApplicationStatus.PENDING, customerApplicationDTO.getStatus());
        assertEquals(new BigDecimal("50000"), customerApplicationDTO.getAnnualIncome());
        assertEquals("ABCDE1234F", customerApplicationDTO.getPanId());
        assertEquals("9876543210", customerApplicationDTO.getMobileNumber());
        assertEquals("ABC Corp", customerApplicationDTO.getCompanyName());
        assertEquals("path/to/income/proof", customerApplicationDTO.getIncomeProofFilePath());
        assertEquals("John", customerApplicationDTO.getName());
        assertEquals("Gold", customerApplicationDTO.getCreditCard());
    }

    @Test
    public void testSetApplicationId() {
        customerApplicationDTO.setApplicationId(2L);
        assertEquals(2L, customerApplicationDTO.getApplicationId());
    }

    @Test
    public void testSetCustomerId() {
        customerApplicationDTO.setCustomerId(1002L);
        assertEquals(1002L, customerApplicationDTO.getCustomerId());
    }

    @Test
    public void testSetCustomerName() {
        customerApplicationDTO.setCustomerName("Jane Doe");
        assertEquals("Jane Doe", customerApplicationDTO.getCustomerName());
    }

    @Test
    public void testSetEmail() {
        customerApplicationDTO.setEmail("jane.doe@example.com");
        assertEquals("jane.doe@example.com", customerApplicationDTO.getEmail());
    }

    @Test
    public void testSetStatus() {
        customerApplicationDTO.setStatus(ApplicationStatus.APPROVED);
        assertEquals(ApplicationStatus.APPROVED, customerApplicationDTO.getStatus());
    }

    @Test
    public void testSetAnnualIncome() {
        customerApplicationDTO.setAnnualIncome(new BigDecimal("60000"));
        assertEquals(new BigDecimal("60000"), customerApplicationDTO.getAnnualIncome());
    }

    @Test
    public void testSetPanId() {
        customerApplicationDTO.setPanId("ABCDE1234G");
        assertEquals("ABCDE1234G", customerApplicationDTO.getPanId());
    }

    @Test
    public void testSetMobileNumber() {
        customerApplicationDTO.setMobileNumber("9876543211");
        assertEquals("9876543211", customerApplicationDTO.getMobileNumber());
    }

    @Test
    public void testSetCompanyName() {
        customerApplicationDTO.setCompanyName("XYZ Corp");
        assertEquals("XYZ Corp", customerApplicationDTO.getCompanyName());
    }

    @Test
    public void testSetIncomeProofFilePath() {
        customerApplicationDTO.setIncomeProofFilePath("path/to/new/income/proof");
        assertEquals("path/to/new/income/proof", customerApplicationDTO.getIncomeProofFilePath());
    }

    @Test
    public void testSetName() {
        customerApplicationDTO.setName("Doe");
        assertEquals("Doe", customerApplicationDTO.getName());
    }

    @Test
    public void testSetCreditCard() {
        customerApplicationDTO.setCreditCard("Platinum");
        assertEquals("Platinum", customerApplicationDTO.getCreditCard());
    }
}
