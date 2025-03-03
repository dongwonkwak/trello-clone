package com.trelloclone.backend.adapter.out.persistence.repository;

import com.trelloclone.backend.adapter.out.persistence.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardRepository extends JpaRepository<CardEntity, UUID> {
}
