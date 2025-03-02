package com.trelloclone.backend.domain.model.card;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record ChecklistId(UUID id) {

    public ChecklistId {
        requireNonNull(id);
    }

    public static ChecklistId of(UUID id) {
        return new ChecklistId(id);
    }

    public static ChecklistId newId() {
        return new ChecklistId(UUID.randomUUID());
    }
}
