package com.trelloclone.backend.adapter.out.persistence.repository;

import com.trelloclone.backend.adapter.out.persistence.entity.BoardMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BoardMemberRepository extends JpaRepository<BoardMemberEntity, UUID> {

    void deleteByBoardId(UUID boardId);

}
