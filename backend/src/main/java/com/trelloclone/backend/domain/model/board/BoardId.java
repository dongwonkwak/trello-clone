package com.trelloclone.backend.domain.model.board;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record BoardId(UUID id) {

    public BoardId {
        requireNonNull(id);
    }

    public static BoardId of(UUID id) {
        return new BoardId(id);
    }

    public static BoardId newId() {
        return new BoardId(UUID.randomUUID());
    }
}
