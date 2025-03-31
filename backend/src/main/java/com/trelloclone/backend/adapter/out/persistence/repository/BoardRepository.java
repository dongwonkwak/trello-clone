package com.trelloclone.backend.adapter.out.persistence.repository;

import com.trelloclone.backend.adapter.out.persistence.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BoardRepository extends JpaRepository<BoardEntity, UUID> {

    // 사용자가 멤버로 참여한 모든 보드 조회
    @Query("SELECT bm.board FROM BoardMemberEntity bm WHERE bm.accountId = :accountId")
    List<BoardEntity> findAllBoardsByAccount(@Param("accountId") UUID accountId);
}
