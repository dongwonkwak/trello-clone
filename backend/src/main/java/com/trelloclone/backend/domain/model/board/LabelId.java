package com.trelloclone.backend.domain.model.board;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record LabelId(UUID id) {

    public LabelId {
        requireNonNull(id);
    }

    public static LabelId of(UUID id) {
        return new LabelId(id);
    }

    public static LabelId newId() {
        return new LabelId(UUID.randomUUID());
    }
}
