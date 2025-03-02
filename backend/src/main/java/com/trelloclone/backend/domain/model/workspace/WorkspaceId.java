package com.trelloclone.backend.domain.model.workspace;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record WorkspaceId(UUID id) {

    public WorkspaceId {
        requireNonNull(id);
    }

    public static WorkspaceId of(UUID id) {
        return new WorkspaceId(id);
    }

    public static WorkspaceId newId() {
        return new WorkspaceId(UUID.randomUUID());
    }
}
