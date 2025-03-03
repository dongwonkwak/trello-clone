package com.trelloclone.backend.adapter.out.persistence.repository;

import com.trelloclone.backend.adapter.out.persistence.entity.AttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<AttachmentEntity, UUID> {
}
