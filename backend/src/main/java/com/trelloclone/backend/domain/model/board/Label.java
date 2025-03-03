package com.trelloclone.backend.domain.model.board;

import com.trelloclone.backend.domain.model.common.BaseEntity;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

public class Label extends BaseEntity {
    private final LabelId id;
    private final BoardId boardId;
    private String name;
    private String color;

    public Label(
            LabelId id,
            BoardId boardId,
            String name,
            String color,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        super(createdAt, updatedAt);

        this.id = requireNonNull(id);
        this.boardId = requireNonNull(boardId);
        this.name = requireNonNull(name);
        this.color = requireNonNull(color);
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
        recordUpdate();
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
}
