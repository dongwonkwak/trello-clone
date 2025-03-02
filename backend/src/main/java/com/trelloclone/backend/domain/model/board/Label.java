package com.trelloclone.backend.domain.model.board;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

public class Label {
    private final LabelId id;
    private final BoardId boardId;
    private String name;
    private String color;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Label(
            LabelId id,
            BoardId boardId,
            String name,
            String color,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = requireNonNull(id);
        this.boardId = requireNonNull(boardId);
        this.name = requireNonNull(name);
        this.color = requireNonNull(color);
        this.createdAt = requireNonNull(createdAt);
        this.updatedAt = updatedAt;
    }

    public static Label create(BoardId boardId, String name, String color) {
        LocalDateTime now = LocalDateTime.now();
        return new Label(
                LabelId.newId(),
                boardId,
                name,
                color,
                now,
                now
        );
    }

    public void update(String name, String color) {
        this.name = requireNonNull(name);
        this.color = requireNonNull(color);
        this.updatedAt = LocalDateTime.now();
    }

    public LabelId getId() {
        return id;
    }

    public BoardId getBoardId() {
        return boardId;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
