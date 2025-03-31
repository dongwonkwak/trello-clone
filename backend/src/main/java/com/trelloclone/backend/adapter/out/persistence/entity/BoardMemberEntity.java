package com.trelloclone.backend.adapter.out.persistence.entity;

import com.trelloclone.backend.domain.model.BoardRole;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "board_members",
uniqueConstraints = {
                @UniqueConstraint(columnNames = {"account_id", "board_id"})
        })
@Data
public class BoardMemberEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "account_id")
    private UUID accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private BoardEntity board;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoardRole role;

    @Column(nullable = false)
    private LocalDateTime joinedAt;

    @PrePersist
    protected void onCreate() {
        if (joinedAt == null) {
            joinedAt = LocalDateTime.now();
        }
        if (role == null) {
            role = BoardRole.MEMBER;
        }
    }
}
