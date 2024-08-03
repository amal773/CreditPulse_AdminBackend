package com.example.util;

import org.junit.jupiter.api.Test;

import com.example.exception.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class HasherTest {

    @Test
    void testHashPassword() throws ResourceNotFoundException {
 

        // Test hashing an empty password
        String emptyPassword = "";
        String expectedEmptyHash = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
        String actualEmptyHash = Hasher.hashPassword(emptyPassword);
        assertEquals(expectedEmptyHash, actualEmptyHash);
    }

    @Test
    void testBytesToHex() throws ResourceNotFoundException {
        // Test converting a known byte array to hex string
        byte[] byteArray = new byte[] { 0x00, 0x12, (byte) 0xFF };
        String expectedHex = "0012ff";
        String actualHex = Hasher.hashPassword("");
        // Access private method via reflection for testing
        String actualHex1 = invokeBytesToHex(byteArray);
        assertEquals(expectedHex, actualHex1);
    }

    // Reflection method to access private method for testing
    private String invokeBytesToHex(byte[] byteArray) {
        try {
            java.lang.reflect.Method method = Hasher.class.getDeclaredMethod("bytesToHex", byte[].class);
            method.setAccessible(true);
            return (String) method.invoke(null, (Object) byteArray);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testNoSuchAlgorithmException() {
        try {
            // Trigger the NoSuchAlgorithmException by using an invalid algorithm
            MessageDigest.getInstance("InvalidAlgorithm");
            fail("Expected NoSuchAlgorithmException was not thrown");
        } catch (NoSuchAlgorithmException e) {
            // Expected exception, test passed
        }
    }
}
