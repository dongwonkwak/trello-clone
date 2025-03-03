package com.trelloclone.backend.adapter.out.persistence.repository;

import com.trelloclone.backend.adapter.out.persistence.entity.WorkspaceInvitationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkspaceInvitationRepository extends JpaRepository<WorkspaceInvitationEntity, UUID> {
}
