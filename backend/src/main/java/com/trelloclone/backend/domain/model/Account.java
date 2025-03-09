package com.trelloclone.backend.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(of = "id")
@Builder(builderClassName = "AccountBuilder", buildMethodName = "_build")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {

    private final Id id;
    private final String username;
    private final String email;
    private final String password;
    private final String fullName;
    private final String profileImageUrl;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder.Default
    private boolean emailVerified = false;

    public static class AccountBuilder {
        public Account build() {
            if (this.id == null) {
                this.id = Id.newId();
            }

            if (this.createdAt == null) {
                this.createdAt = LocalDateTime.now();
            }

            return _build();
        }
    }

    public AccountBuilder toBuilder() {
        var builder = new AccountBuilder()
                .id(this.id)
                .username(this.username)
                .email(this.email)
                .password(this.password)
                .fullName(this.fullName)
                .profileImageUrl(this.profileImageUrl)
                .emailVerified(false)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now());

        return builder;
    }

    public static AccountBuilder builder() {
        return new AccountBuilder();
    }

    public Account verifyEmail() {
        return this.toBuilder()
                .emailVerified(true)
                .build();
    }
}
