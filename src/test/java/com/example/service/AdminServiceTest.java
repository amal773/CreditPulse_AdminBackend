package com.example.service;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.model.Admin;
import com.example.repository.AdminRepository;

import java.util.Optional;

public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginAdminService_Success() {
        // Setup
        Admin admin = new Admin();
        admin.setUsername("admin@example.com");
        admin.setPassword("password123");
        Optional<Admin> optionalAdmin = Optional.of(admin);

        when(adminRepository.findByUsernameAndPassword("admin@example.com", "password123")).thenReturn(optionalAdmin);

        // Execution
        ResponseEntity<String> response = adminService.loginAdminService(admin);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Admin logged in successfully!", response.getBody());

        // Ensure interaction with mock
        verify(adminRepository).findByUsernameAndPassword("admin@example.com", "password123");
    }

    @Test
    public void testLoginAdminService_Failure() {
        // Setup
        Admin admin = new Admin();
        admin.setUsername("admin@example.com");
        admin.setPassword("wrongpassword");
        Optional<Admin> optionalAdmin = Optional.empty();

        when(adminRepository.findByUsernameAndPassword("admin@example.com", "wrongpassword")).thenReturn(optionalAdmin);

        // Execution
        ResponseEntity<String> response = adminService.loginAdminService(admin);

        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody());

        // Ensure interaction with mock
        verify(adminRepository).findByUsernameAndPassword("admin@example.com", "wrongpassword");
    }
}
