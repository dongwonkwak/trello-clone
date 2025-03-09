package com.trelloclone.backend.domain.model;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record Id(UUID id) {
    public Id {
        requireNonNull(id, "id must not be null");
    }

    public static Id of(UUID id) {
        return new Id(id);
    }

    public static Id newId() {
        return new Id(UUID.randomUUID());
    }
}
