package com.trelloclone.backend.domain.model.workspace;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record WorkspaceInvitationId(UUID id) {

    public WorkspaceInvitationId {
        requireNonNull(id);
    }

    public static WorkspaceInvitationId of(UUID id) {
        return new WorkspaceInvitationId(id);
    }

    public static WorkspaceInvitationId newId() {
        return new WorkspaceInvitationId(UUID.randomUUID());
    }
}
