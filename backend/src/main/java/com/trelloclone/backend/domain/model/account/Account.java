package com.trelloclone.backend.domain.model.account;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

public class Account {
    private final AccountId id;
    private String username;
    private String email;
    private String fullName;
    private String profileImageUrl;
    private boolean emailVerified;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Account(
            AccountId id,
            String username,
            String email,
            String fullName,
            String profileImageUrl,
            boolean emailVerified,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = requireNonNull(id);
        this.username = requireNonNull(username);
        this.email = requireNonNull(email);
        this.fullName = fullName;
        this.profileImageUrl = profileImageUrl;
        this.emailVerified = emailVerified;
        this.createdAt = requireNonNull(createdAt);
        this.updatedAt = updatedAt;
    }

    public static Account create(
            String username,
            String email,
            String fullName,
            String profileImageUrl) {
        return new Account(
                AccountId.newId(),
                username,
                email,
                fullName,
                profileImageUrl,
                false,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public void updateProfile(String fullName, String profileImageUrl) {
        this.fullName = fullName;
        this.profileImageUrl = profileImageUrl;
        this.updatedAt = LocalDateTime.now();
    }

    public void verifyEmail() {
        this.emailVerified = true;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public AccountId getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
