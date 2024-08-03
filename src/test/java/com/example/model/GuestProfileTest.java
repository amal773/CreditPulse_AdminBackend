package com.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GuestProfileTest {

    private GuestProfile guestProfile;
    private CreditCard creditCard;
    private Admin admin;

    @BeforeEach
    public void setUp() {
        creditCard = new CreditCard();
        creditCard.setCardType("VISA");

        admin = new Admin();
        admin.setUsername("admin1");

        guestProfile = new GuestProfile(
            "guest@example.com",
            "password123",
            1L,
            "1234567890",
            "John Doe",
            "ABCDE1234F",
            123456789012L,
            "1234 Main St, Anytown, USA",
            new Date(90, 0, 1), // January 1, 1990
            5,
            "Example Company",
            new BigDecimal("50000.00"),
            "/path/to/income/proof",
            creditCard,
            ApplicationStatus.PENDING,
            "/path/to/aadhaar",
            "/path/to/pan",
            "/path/to/signature",
            "/path/to/photo",
            admin
        );
    }

    @Test
    public void testGettersAndSetters() {
        // Test guestEmail
        assertEquals("guest@example.com", guestProfile.getGuestEmail());
        guestProfile.setGuestEmail("new@example.com");
        assertEquals("new@example.com", guestProfile.getGuestEmail());

        // Test password
        assertEquals("password123", guestProfile.getPassword());
        guestProfile.setPassword("newPassword");
        assertEquals("newPassword", guestProfile.getPassword());

        // Test applicationId
        assertEquals(1L, guestProfile.getApplicationId());
        guestProfile.setApplicationId(2L);
        assertEquals(2L, guestProfile.getApplicationId());

        // Test mobileNumber
        assertEquals("1234567890", guestProfile.getMobileNumber());
        guestProfile.setMobileNumber("0987654321");
        assertEquals("0987654321", guestProfile.getMobileNumber());

        // Test name
        assertEquals("John Doe", guestProfile.getName());
        guestProfile.setName("Jane Doe");
        assertEquals("Jane Doe", guestProfile.getName());

        // Test panId
        assertEquals("ABCDE1234F", guestProfile.getPanId());
        guestProfile.setPanId("XYZ1234567");
        assertEquals("XYZ1234567", guestProfile.getPanId());

        // Test aadhaarNumber
        assertEquals(Long.valueOf(123456789012L), guestProfile.getAadhaarNumber());
        guestProfile.setAadhaarNumber(987654321098L);
        assertEquals(Long.valueOf(987654321098L), guestProfile.getAadhaarNumber());

        // Test address
        assertEquals("1234 Main St, Anytown, USA", guestProfile.getAddress());
        guestProfile.setAddress("4321 Any Street, Anytown, USA");
        assertEquals("4321 Any Street, Anytown, USA", guestProfile.getAddress());

        // Test dob
        assertNotNull(guestProfile.getDob());
        Date newDob = new Date(88, 0, 1); // January 1, 1988
        guestProfile.setDob(newDob);
        assertEquals(newDob, guestProfile.getDob());

        // Test employmentYears
        assertEquals(5, guestProfile.getEmploymentYears());
        guestProfile.setEmploymentYears(10);
        assertEquals(10, guestProfile.getEmploymentYears());

        // Test companyName
        assertEquals("Example Company", guestProfile.getCompanyName());
        guestProfile.setCompanyName("New Company");
        assertEquals("New Company", guestProfile.getCompanyName());

        // Test annualIncome
        assertEquals(0, new BigDecimal("50000.00").compareTo(guestProfile.getAnnualIncome()));
        guestProfile.setAnnualIncome(new BigDecimal("60000.00"));
        assertEquals(0, new BigDecimal("60000.00").compareTo(guestProfile.getAnnualIncome()));

        // Test incomeProofFilePath
        assertEquals("/path/to/income/proof", guestProfile.getIncomeProofFilePath());
        guestProfile.setIncomeProofFilePath("/new/path/to/income/proof");
        assertEquals("/new/path/to/income/proof", guestProfile.getIncomeProofFilePath());

        // Test creditCard
        assertNotNull(guestProfile.getCreditCard());
        CreditCard newCreditCard = new CreditCard();
        guestProfile.setCreditCard(newCreditCard);
        assertEquals(newCreditCard, guestProfile.getCreditCard());

        // Test applicationStatus
        assertEquals(ApplicationStatus.PENDING, guestProfile.getApplicationStatus());
        guestProfile.setApplicationStatus(ApplicationStatus.APPROVED);
        assertEquals(ApplicationStatus.APPROVED, guestProfile.getApplicationStatus());

        // Test aadhaarFilePath
        assertEquals("/path/to/aadhaar", guestProfile.getAadhaarFilePath());
        guestProfile.setAadhaarFilePath("/new/path/to/aadhaar");
        assertEquals("/new/path/to/aadhaar", guestProfile.getAadhaarFilePath());

        // Test panFilePath
        assertEquals("/path/to/pan", guestProfile.getPanFilePath());
        guestProfile.setPanFilePath("/new/path/to/pan");
        assertEquals("/new/path/to/pan", guestProfile.getPanFilePath());

        // Test signatureFilePath
        assertEquals("/path/to/signature", guestProfile.getSignatureFilePath());
        guestProfile.setSignatureFilePath("/new/path/to/signature");
        assertEquals("/new/path/to/signature", guestProfile.getSignatureFilePath());

        // Test photoFilePath
        assertEquals("/path/to/photo", guestProfile.getPhotoFilePath());
        guestProfile.setPhotoFilePath("/new/path/to/photo");
        assertEquals("/new/path/to/photo", guestProfile.getPhotoFilePath());

        // Test admin
        assertNotNull(guestProfile.getAdmin());
        Admin newAdmin = new Admin("newAdmin", "newPassword");
        guestProfile.setAdmin(newAdmin);
        assertEquals(newAdmin, guestProfile.getAdmin());
    }
}
