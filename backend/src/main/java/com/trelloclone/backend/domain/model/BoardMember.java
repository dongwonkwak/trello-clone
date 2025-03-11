package com.trelloclone.backend.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder(builderClassName = "BoardMemberBuilder", buildMethodName = "_build")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardMember {

    private final Account account;
    private final Board board;
    private final BoardRole role;
    private final LocalDateTime joinedAt;

    public static class BoardMemberBuilder {
        public BoardMember build() {
            if (this.joinedAt == null) {
                this.joinedAt = LocalDateTime.now();
            }

            return _build();
        }
    }

    public BoardMemberBuilder toBuilder() {
        return new BoardMemberBuilder()
                .account(this.account)
                .board(this.board)
                .role(this.role)
                .joinedAt(this.joinedAt);
    }

    public static BoardMemberBuilder builder() {
        return new BoardMemberBuilder();
    }
}
