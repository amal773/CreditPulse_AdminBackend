package com.example.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class KeyGeneratorTest {

    private KeyGenerator keyGenerator;

    @BeforeEach
    public void setUp() {
        keyGenerator = new KeyGenerator();
    }

    @Test
    public void testGenerateUniqueCardNumber() {
        Long cardNumber = keyGenerator.generateUniqueCardNumber();
        assertNotNull(cardNumber);
        assertTrue(cardNumber >= 1000000000L && cardNumber <= 9999999999L);
    }

    @Test
    public void testGeneratePin() {
        int pin = keyGenerator.generatePin();
        assertTrue(pin >= 1000 && pin <= 9999);
    }

    @Test
    public void testGenerateCvv() {
        String cvv = keyGenerator.generateCvv();
        assertNotNull(cvv);
        assertTrue(cvv.matches("\\d{3}"));
    }

    @RepeatedTest(10)
    public void testGeneratePassword() {
        String password = keyGenerator.generatePassword();
        assertNotNull(password);
        assertEquals(12, password.length());

        // Password pattern: at least one upper case, one lower case, one digit, and one special character
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+<>?]).{12}$";
        Pattern pattern = Pattern.compile(regex);
        assertTrue(pattern.matcher(password).matches());
    }
}
