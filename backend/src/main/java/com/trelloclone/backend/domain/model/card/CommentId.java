package com.trelloclone.backend.domain.model.card;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record CommentId(UUID id) {
    public CommentId {
        requireNonNull(id);
    }

    public static CommentId of(UUID id) {
        return new CommentId(id);
    }

    public static CommentId newId() {
        return new CommentId(UUID.randomUUID());
    }
}
