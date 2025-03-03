package com.trelloclone.backend.domain.model.workspace;

import com.trelloclone.backend.domain.model.account.AccountId;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

public class WorkspaceInvitation {
    private final WorkspaceInvitationId id;
    private final WorkspaceId workspaceId;
    private final String email;
    private final WorkspaceMemberRole role;
    private InvitationStatus status;
    private final AccountId invitedBy;
    private final LocalDateTime createdAt;
    private final LocalDateTime expiresAt;

    public WorkspaceInvitation(
            WorkspaceInvitationId id,
            WorkspaceId workspaceId,
            String email,
            WorkspaceMemberRole role,
            InvitationStatus status,
            AccountId invitedBy,
            LocalDateTime createdAt,
            LocalDateTime expiresAt) {
        this.id = requireNonNull(id);
        this.workspaceId = requireNonNull(workspaceId);
        this.email = requireNonNull(email);
        this.role = requireNonNull(role);
        this.status = requireNonNull(status);
        this.invitedBy = requireNonNull(invitedBy);
        this.createdAt = requireNonNull(createdAt);
        this.expiresAt = requireNonNull(expiresAt);
    }

    public static WorkspaceInvitation create(
            WorkspaceId workspaceId,
            String email,
            WorkspaceMemberRole role,
            AccountId invitedBy,
            int expirationDays) {
        LocalDateTime now = LocalDateTime.now();
        return new WorkspaceInvitation(
                WorkspaceInvitationId.newId(),
                workspaceId,
                email,
                role,
                InvitationStatus.PENDING,
                invitedBy,
                now,
                now.plusDays(expirationDays)
        );
    }

    public void accept() {
        if (isExpired()) {
            throw new IllegalStateException("Cannot accept expired invitation");
        }
        this.status = InvitationStatus.ACCEPTED;
    }

    public void decline() {
        if (isExpired()) {
            throw new IllegalStateException("Cannot decline expired invitation");
        }
        this.status = InvitationStatus.DECLINED;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt) || status == InvitationStatus.EXPIRED;
    }

    public boolean isPending() {
        return status == InvitationStatus.PENDING && !isExpired();
    }

    public WorkspaceInvitationId getId() {
        return id;
    }

    public WorkspaceId getWorkspaceId() {
        return workspaceId;
    }

    public String getEmail() {
        return email;
    }

    public WorkspaceMemberRole getRole() {
        return role;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public AccountId getInvitedBy() {
        return invitedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public enum InvitationStatus {
        PENDING,   // 초대가 전송되고 응답 대기 중
        ACCEPTED,  // 초대가 수락됨
        DECLINED,  // 초대가 거절됨
        EXPIRED    // 초대 만료됨
    }
}
