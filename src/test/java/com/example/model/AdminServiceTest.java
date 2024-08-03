package com.example.model;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.model.Admin;
import com.example.repository.AdminRepository;
import com.example.service.AdminService;

public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginAdmin_Success() {
        Admin admin = new Admin("admin", "password");
        when(adminRepository.findByUsernameAndPassword(anyString(), anyString())).thenReturn(Optional.of(admin));

        ResponseEntity<String> response = adminService.loginAdminService(new Admin("admin", "password"));
        assertEquals(ResponseEntity.ok("Admin logged in successfully!"), response);
    }

    @Test
    public void testLoginAdmin_Failure() {
        when(adminRepository.findByUsernameAndPassword(anyString(), anyString())).thenReturn(Optional.empty());

        ResponseEntity<String> response = adminService.loginAdminService(new Admin("admin", "wrongpassword"));
        assertEquals(ResponseEntity.badRequest().body("Invalid username or password"), response);
    }
}
