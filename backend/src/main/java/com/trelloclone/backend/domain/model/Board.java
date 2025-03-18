package com.trelloclone.backend.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Board {

    private final Id id;
    private final String title;
    private final String description;
    private final boolean isPublic;
    private final String backgroundColor;
    private final Account owner;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public BoardBuilder toBuilder() {
        return new BoardBuilder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .isPublic(this.isPublic)
                .backgroundColor(this.backgroundColor)
                .owner(this.owner)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt);
    }

    public static BoardBuilder builder() {
        return new BoardBuilder();
    }
}
