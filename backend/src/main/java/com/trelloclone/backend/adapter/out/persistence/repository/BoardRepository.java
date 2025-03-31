package com.trelloclone.backend.adapter.out.persistence.repository;

import com.trelloclone.backend.adapter.out.persistence.entity.AccountEntity;
import com.trelloclone.backend.adapter.out.persistence.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BoardRepository extends JpaRepository<BoardEntity, UUID> {
    // 사용자가 소유한 보드 조회
    List<BoardEntity> findByOwner(AccountEntity owner);


    // 사용자가 멤버로 참여한 보드 조회
    @Query("SELECT bm.board FROM BoardMemberEntity bm WHERE bm.account = :account")
    List<BoardEntity> findBoardsByMember(@Param("account") AccountEntity account);


    // 사용자가 소유하거나 멤버로 참여한 모든 보드 조회
    @Query(value = "SELECT b FROM BoardEntity b WHERE b.owner = :owner " +
            "UNION " +
            "SELECT bm.board FROM BoardMemberEntity bm WHERE bm.account = :owner", nativeQuery = true)
    List<BoardEntity> findAllBoardsByAccount(@Param("owner") AccountEntity owner);

}
