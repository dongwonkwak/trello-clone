package com.trelloclone.backend.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Board {

    @Builder.Default
    private final Id id = Id.newId();
    private final String title;
    private final String description;
    private final boolean isPublic;
    private final String backgroundColor;
    private final Account owner;

    @Builder.Default
    private final LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    private final LocalDateTime updatedAt = LocalDateTime.now();

    public BoardBuilder toBuilder() {
        return new BoardBuilder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .isPublic(this.isPublic)
                .backgroundColor(this.backgroundColor)
                .owner(this.owner)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now());
    }

    public static BoardBuilder builder() {
        return new BoardBuilder();
    }
}
