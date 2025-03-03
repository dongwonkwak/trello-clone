package com.trelloclone.backend.adapter.out.persistence.repository;

import com.trelloclone.backend.adapter.out.persistence.entity.BoardListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BoardListRepository extends JpaRepository<BoardListEntity, UUID> {
}
