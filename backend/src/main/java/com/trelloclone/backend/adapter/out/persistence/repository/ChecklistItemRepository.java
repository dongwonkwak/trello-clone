package com.trelloclone.backend.adapter.out.persistence.repository;

import com.trelloclone.backend.adapter.out.persistence.entity.ChecklistItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChecklistItemRepository extends JpaRepository<ChecklistItemEntity, UUID> {
}
