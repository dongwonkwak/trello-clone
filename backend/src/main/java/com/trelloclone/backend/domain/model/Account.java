package com.trelloclone.backend.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {

    private final Id id;
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String profileImageUrl;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder.Default
    private boolean emailVerified = false;

    public AccountBuilder toBuilder() {
        return new AccountBuilder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .profileImageUrl(this.profileImageUrl)
                .emailVerified(this.emailVerified)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt);
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
