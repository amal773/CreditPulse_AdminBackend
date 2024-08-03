package com.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreditCardTest {

    private CreditCard creditCard;

    @BeforeEach
    public void setUp() {
        creditCard = new CreditCard();
    }

    @Test
    public void testGetAndSetCardType() {
        String cardType = "VISA";
        creditCard.setCardType(cardType);
        assertEquals(cardType, creditCard.getCardType());
    }

    @Test
    public void testGetAndSetMaxLimit() {
        BigDecimal maxLimit = new BigDecimal("50000.0000");
        creditCard.setMaxLimit(maxLimit);
        assertEquals(maxLimit, creditCard.getMaxLimit());
    }

    @Test
    public void testGetAndSetInterest() {
        BigDecimal interest = new BigDecimal("1.5");
        creditCard.setInterest(interest);
        assertEquals(interest, creditCard.getInterest());
    }

    @Test
    public void testGetAndSetAnnualFee() {
        BigDecimal annualFee = new BigDecimal("499.99");
        creditCard.setAnnualFee(annualFee);
        assertEquals(annualFee, creditCard.getAnnualFee());
    }

    @Test
    public void testCreditCardConstructor() {
        String cardType = "MASTERCARD";
        BigDecimal maxLimit = new BigDecimal("75000.0000");
        BigDecimal interest = new BigDecimal("2.5");
        BigDecimal annualFee = new BigDecimal("999.99");

        CreditCard newCard = new CreditCard(cardType, maxLimit, interest, annualFee);

        assertNotNull(newCard);
        assertEquals(cardType, newCard.getCardType());
        assertEquals(maxLimit, newCard.getMaxLimit());
        assertEquals(interest, newCard.getInterest());
        assertEquals(annualFee, newCard.getAnnualFee());
    }

    @Test
    public void testCreditCardDefaultConstructor() {
        CreditCard newCard = new CreditCard();
        assertNotNull(newCard);
    }
}
