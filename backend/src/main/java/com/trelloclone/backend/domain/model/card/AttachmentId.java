package com.trelloclone.backend.domain.model.card;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record AttachmentId(UUID id) {
    public AttachmentId {
        requireNonNull(id);
    }

    public static AttachmentId of(UUID id) {
        return new AttachmentId(id);
    }

    public static AttachmentId newId() {
        return new AttachmentId(UUID.randomUUID());
    }
}
