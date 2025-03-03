package com.trelloclone.backend.adapter.out.persistence.repository;

import com.trelloclone.backend.adapter.out.persistence.entity.WorkspaceMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMemberEntity, UUID> {
}
