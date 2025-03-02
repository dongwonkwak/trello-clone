package com.trelloclone.backend.domain.model.workspace;

import com.trelloclone.backend.domain.model.account.AccountId;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class WorkspaceMember {
    private final AccountId accountId;
    private final WorkspaceId workspaceId;
    private WorkspaceMemberRole role;
    private final LocalDateTime joinedAt;

    public WorkspaceMember(AccountId accountId, WorkspaceId workspaceId, WorkspaceMemberRole role) {
        this.accountId = requireNonNull(accountId);
        this.workspaceId = requireNonNull(workspaceId);
        this.role = requireNonNull(role);
        this.joinedAt = LocalDateTime.now();
    }

    public void changeRole(WorkspaceMemberRole role) {
        this.role = requireNonNull(role);
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public WorkspaceId getWorkspaceId() {
        return workspaceId;
    }

    public WorkspaceMemberRole getRole() {
        return role;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkspaceMember that = (WorkspaceMember) o;
        return Objects.equals(accountId, that.accountId) &&
                Objects.equals(workspaceId, that.workspaceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, workspaceId);
    }
}
