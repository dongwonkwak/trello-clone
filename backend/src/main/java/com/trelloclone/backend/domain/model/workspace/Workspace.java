package com.trelloclone.backend.domain.model.workspace;

import com.trelloclone.backend.domain.model.account.AccountId;
import com.trelloclone.backend.domain.model.common.BaseEntity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class Workspace extends BaseEntity {
    private final WorkspaceId id;
    private String name;
    private String description;
    private final AccountId ownerId;
    private final Set<WorkspaceMember> members;

    public Workspace(
            WorkspaceId id,
            String name,
            String description,
            AccountId ownerId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        super(createdAt, updatedAt);

        this.id = requireNonNull(id);
        this.name = requireNonNull(name);
        this.description = description;
        this.ownerId = requireNonNull(ownerId);
        this.members = new HashSet<>();
    }

    public static Workspace create(String name, String description, AccountId ownerId) {
        LocalDateTime now = LocalDateTime.now();
        Workspace workspace = new Workspace(
                WorkspaceId.newId(),
                name,
                description,
                ownerId,
                now,
                now
        );

        // 소유자를 관리자 역할로 추가
        workspace.addMember(ownerId, WorkspaceMemberRole.ADMIN);

        return workspace;
    }

    public void update(String name, String description) {
        this.name = requireNonNull(name);
        this.description = description;
        recordUpdate();
    }

    public void addMember(AccountId accountId, WorkspaceMemberRole role) {
        members.add(new WorkspaceMember(accountId, id, role));
    }

    public void removeMember(AccountId accountId) {
        if (accountId.equals(this.ownerId)) {
            throw new IllegalStateException("Cannot remove the workspace owner");
        }
        this.members.removeIf(member -> member.getAccountId().equals(accountId));
    }

    public void changeMemberRole(AccountId accountId, WorkspaceMemberRole newRole) {
        this.members.stream()
                .filter(member -> member.getAccountId().equals(accountId))
                .findFirst()
                .ifPresent(member -> member.changeRole(newRole));
    }

    public boolean isMember(AccountId accountId) {
        return this.members.stream()
                .anyMatch(member -> member.getAccountId().equals(accountId));
    }

    public boolean isOwner(AccountId accountId) {
        return this.ownerId.equals(accountId);
    }

    public boolean canManage(AccountId accountId) {
        return isOwner(accountId) || this.members.stream()
                .anyMatch(member -> member.getAccountId().equals(accountId) &&
                        member.getRole() == WorkspaceMemberRole.ADMIN);
    }

    public WorkspaceId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public AccountId getOwnerId() {
        return ownerId;
    }

    public Set<WorkspaceMember> getMembers() {
        return new HashSet<>(members);
    }
}
