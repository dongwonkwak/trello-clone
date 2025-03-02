package com.trelloclone.backend.domain.model.card;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record CardId(UUID id) {

    public CardId {
        requireNonNull(id);
    }

    public static CardId of(UUID id) {
        return new CardId(id);
    }

    public static CardId newId() {
        return new CardId(UUID.randomUUID());
    }
}
