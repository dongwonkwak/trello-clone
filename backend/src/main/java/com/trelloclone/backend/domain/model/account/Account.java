package com.trelloclone.backend.domain.model.account;

import com.trelloclone.backend.domain.model.common.BaseEntity;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

public class Account extends BaseEntity {
    private final AccountId id;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String profileImageUrl;
    private boolean emailVerified;

    public Account(
            AccountId id,
            String username,
            String email,
            String password,
            String fullName,
            String profileImageUrl,
            boolean emailVerified,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);

        this.id = requireNonNull(id);
        this.username = requireNonNull(username);
        this.email = requireNonNull(email);
        this.password = requireNonNull(password);
        this.fullName = fullName;
        this.profileImageUrl = profileImageUrl;
        this.emailVerified = emailVerified;
    }

    public static Account create(
            String username,
            String email,
            String password,
            String fullName,
            String profileImageUrl) {
        return new Account(
                AccountId.newId(),
                username,
                email,
                password,
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
        recordUpdate();
    }

    public void verifyEmail() {
        this.emailVerified = true;
        recordUpdate();
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

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
