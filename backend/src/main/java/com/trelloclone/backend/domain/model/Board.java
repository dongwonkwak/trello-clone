package com.trelloclone.backend.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode(of = "id")
@Builder(builderClassName = "BoardBuilder", buildMethodName = "_build")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Board {

    private final Id id;
    private final String title;
    private final String description;
    private final boolean isPublic;
    private final String backgroundColor;
    private final Account owner;

    @Singular
    private final List<BoardMember> members;
    // private final List<BoardList> lists;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static class BoardBuilder extends BaseEntityBuilder<Board, BoardBuilder> {

        @Override
        public Board build() {
            setupDefaults();
            return _build();
        }
    }

    public BoardBuilder toBuilder() {
        return new BoardBuilder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .isPublic(this.isPublic)
                .backgroundColor(this.backgroundColor)
                .owner(this.owner)
                .members(new ArrayList<>(this.members))
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now());
    }

    public static BoardBuilder builder() {
        return new BoardBuilder();
    }
}
