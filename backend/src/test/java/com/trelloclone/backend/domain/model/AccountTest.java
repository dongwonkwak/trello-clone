package com.trelloclone.backend.domain.model;

import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void testAccountBuilder() {
        Account account = Account.builder()
                .email("test@example.com")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .profileImageUrl("http://example.com/image.jpg")
                .build();

        assertNotNull(account.getId());
        assertEquals("test@example.com", account.getEmail());
        assertEquals("password", account.getPassword());
        assertEquals("John", account.getFirstName());
        assertEquals("Doe", account.getLastName());
        assertEquals("http://example.com/image.jpg", account.getProfileImageUrl());
        assertFalse(account.isEmailVerified());
        assertNotNull(account.getCreatedAt());
        assertNotNull(account.getUpdatedAt());
    }

    @Test
    void testVerifyEmail() {
        Account account = Account.builder()
                .email("test@example.com")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .profileImageUrl("http://example.com/image.jpg")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Account verifiedAccount = account.verifyEmail();

        assertTrue(verifiedAccount.isEmailVerified());
    }
}
