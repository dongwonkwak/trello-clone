package com.trelloclone.backend.domain.model.card;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record ChecklistItemId(UUID id) {

    public ChecklistItemId {
        requireNonNull(id);
    }

    public static ChecklistItemId of(UUID id) {
        return new ChecklistItemId(id);
    }

    public static ChecklistItemId newId() {
        return new ChecklistItemId(UUID.randomUUID());
    }
}
