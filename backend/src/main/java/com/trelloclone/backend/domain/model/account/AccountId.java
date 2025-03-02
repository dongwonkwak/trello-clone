package com.trelloclone.backend.domain.model.account;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record AccountId(
        UUID id
) {
    public AccountId {
        requireNonNull(id);
    }

    public static AccountId of(UUID id) {
        return new AccountId(id);
    }

    public static AccountId newId() {
        return new AccountId(UUID.randomUUID());
    }
}
