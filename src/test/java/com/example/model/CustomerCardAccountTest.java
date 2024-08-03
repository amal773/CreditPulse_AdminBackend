package com.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerCardAccountTest {

    private CustomerCardAccount customerCardAccount;
    private CustomerProfile customerProfile;
    private CreditCard creditCard;
    private Date openingDate;
    private Date dueDate;
    private Date expiryDate;

    @BeforeEach
    public void setUp() {
        customerProfile = new CustomerProfile();
        creditCard = new CreditCard();
        openingDate = new Date();
        dueDate = new Date();
        expiryDate = new Date();

        customerCardAccount = new CustomerCardAccount(
            1234567890L,
            customerProfile,
            "USD",
            openingDate,
            CustomerCardAccount.ActivationStatus.ACTIVE,
            9876543210L,
            CustomerCardAccount.ActivationStatus.INACTIVE,
            CustomerCardAccount.PaymentStatus.ENABLE,
            CustomerCardAccount.PaymentStatus.DISABLE,
            1234,
            CustomerCardAccount.PaymentStatus.ENABLE,
            BigDecimal.valueOf(1000.0),
            BigDecimal.valueOf(500.0),
            BigDecimal.valueOf(2000.0),
            BigDecimal.valueOf(3000.0),
            BigDecimal.valueOf(100.0),
            dueDate,
            expiryDate,
            "123",
            BigDecimal.valueOf(5000.0),
            "CREDIT_CARD"
        );
    }

    @Test
    public void testDefaultConstructor() {
        CustomerCardAccount defaultCustomerCardAccount = new CustomerCardAccount();
        assertNotNull(defaultCustomerCardAccount);
    }

    @Test
    public void testParameterizedConstructor() {
        assertEquals(1234567890L, customerCardAccount.getAccountNumber());
        assertEquals(customerProfile, customerCardAccount.getCustomerProfile());
        assertEquals("USD", customerCardAccount.getBaseCurrency());
        assertEquals(openingDate, customerCardAccount.getOpeningDate());
        assertEquals(CustomerCardAccount.ActivationStatus.ACTIVE, customerCardAccount.getActivationStatus());
        assertEquals(9876543210L, customerCardAccount.getCardNumber());
        assertEquals(CustomerCardAccount.ActivationStatus.INACTIVE, customerCardAccount.getCardStatus());
        assertEquals(CustomerCardAccount.PaymentStatus.ENABLE, customerCardAccount.getInternationalPayment());
        assertEquals(CustomerCardAccount.PaymentStatus.DISABLE, customerCardAccount.getOnlinePayment());
        assertEquals(1234, customerCardAccount.getPin());
        assertEquals(CustomerCardAccount.PaymentStatus.ENABLE, customerCardAccount.getCardSwipe());
        assertEquals(BigDecimal.valueOf(1000.0), customerCardAccount.getOnlinePaymentLimit());
        assertEquals(BigDecimal.valueOf(500.0), customerCardAccount.getCardSwipeLimit());
        assertEquals(BigDecimal.valueOf(2000.0), customerCardAccount.getInternationalPaymentLimit());
        assertEquals(BigDecimal.valueOf(3000.0), customerCardAccount.getCardBalance());
        assertEquals(BigDecimal.valueOf(100.0), customerCardAccount.getDueAmount());
        assertEquals(dueDate, customerCardAccount.getDueDate());
        assertEquals(expiryDate, customerCardAccount.getExpiryDate());
        assertEquals("123", customerCardAccount.getCvv());
        assertEquals(BigDecimal.valueOf(5000.0), customerCardAccount.getCreditCardLimit());
        assertEquals("CREDIT_CARD", customerCardAccount.getCreditCard());
    }

    @Test
    public void testSettersAndGetters() {
        customerCardAccount.setAccountNumber(1111111111L);
        assertEquals(1111111111L, customerCardAccount.getAccountNumber());

        customerCardAccount.setCustomerProfile(new CustomerProfile());
        assertNotNull(customerCardAccount.getCustomerProfile());

        customerCardAccount.setBaseCurrency("EUR");
        assertEquals("EUR", customerCardAccount.getBaseCurrency());

        Date newOpeningDate = new Date();
        customerCardAccount.setOpeningDate(newOpeningDate);
        assertEquals(newOpeningDate, customerCardAccount.getOpeningDate());

        customerCardAccount.setActivationStatus(CustomerCardAccount.ActivationStatus.INACTIVE);
        assertEquals(CustomerCardAccount.ActivationStatus.INACTIVE, customerCardAccount.getActivationStatus());

        customerCardAccount.setCardNumber(2222222222L);
        assertEquals(2222222222L, customerCardAccount.getCardNumber());

        customerCardAccount.setCardStatus(CustomerCardAccount.ActivationStatus.ACTIVE);
        assertEquals(CustomerCardAccount.ActivationStatus.ACTIVE, customerCardAccount.getCardStatus());

        customerCardAccount.setInternationalPayment(CustomerCardAccount.PaymentStatus.DISABLE);
        assertEquals(CustomerCardAccount.PaymentStatus.DISABLE, customerCardAccount.getInternationalPayment());

        customerCardAccount.setOnlinePayment(CustomerCardAccount.PaymentStatus.ENABLE);
        assertEquals(CustomerCardAccount.PaymentStatus.ENABLE, customerCardAccount.getOnlinePayment());

        customerCardAccount.setPin(5678);
        assertEquals(5678, customerCardAccount.getPin());

        customerCardAccount.setCardSwipe(CustomerCardAccount.PaymentStatus.DISABLE);
        assertEquals(CustomerCardAccount.PaymentStatus.DISABLE, customerCardAccount.getCardSwipe());

        customerCardAccount.setOnlinePaymentLimit(BigDecimal.valueOf(2000.0));
        assertEquals(BigDecimal.valueOf(2000.0), customerCardAccount.getOnlinePaymentLimit());

        customerCardAccount.setCardSwipeLimit(BigDecimal.valueOf(1000.0));
        assertEquals(BigDecimal.valueOf(1000.0), customerCardAccount.getCardSwipeLimit());

        customerCardAccount.setInternationalPaymentLimit(BigDecimal.valueOf(3000.0));
        assertEquals(BigDecimal.valueOf(3000.0), customerCardAccount.getInternationalPaymentLimit());

        customerCardAccount.setCardBalance(BigDecimal.valueOf(4000.0));
        assertEquals(BigDecimal.valueOf(4000.0), customerCardAccount.getCardBalance());

        customerCardAccount.setDueAmount(BigDecimal.valueOf(200.0));
        assertEquals(BigDecimal.valueOf(200.0), customerCardAccount.getDueAmount());

        Date newDueDate = new Date();
        customerCardAccount.setDueDate(newDueDate);
        assertEquals(newDueDate, customerCardAccount.getDueDate());

        Date newExpiryDate = new Date();
        customerCardAccount.setExpiryDate(newExpiryDate);
        assertEquals(newExpiryDate, customerCardAccount.getExpiryDate());

        customerCardAccount.setCvv("456");
        assertEquals("456", customerCardAccount.getCvv());

        customerCardAccount.setCreditCardLimit(BigDecimal.valueOf(6000.0));
        assertEquals(BigDecimal.valueOf(6000.0), customerCardAccount.getCreditCardLimit());

        customerCardAccount.setCreditCard("NEW_CREDIT_CARD");
        assertEquals("NEW_CREDIT_CARD", customerCardAccount.getCreditCard());
    }
}
