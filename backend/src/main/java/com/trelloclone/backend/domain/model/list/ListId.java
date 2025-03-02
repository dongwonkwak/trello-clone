package com.trelloclone.backend.domain.model.list;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record ListId(UUID id) {

    public ListId {
        requireNonNull(id);
    }

    public static ListId of(UUID id) {
        return new ListId(id);
    }

    public static ListId newId() {
        return new ListId(UUID.randomUUID());
    }
}
