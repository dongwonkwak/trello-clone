package com.trelloclone.backend.adapter.out.persistence.mapper;

import com.trelloclone.backend.adapter.out.persistence.entity.BoardEntity;
import com.trelloclone.backend.domain.model.Board;
import com.trelloclone.backend.domain.model.Id;

import java.util.Random;

public class BoardPersistenceMapper {

    private static final Random random = new Random();

    private BoardPersistenceMapper() {

    }

    public static Board toDomain(BoardEntity entity) {
        return Board.builder()
                .id(Id.of(entity.getId()))
                .title(entity.getTitle())
                .description(entity.getDescription())
                .backgroundColor(entity.getBackgroundColor())
                .isPublic(entity.isPublic())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static BoardEntity toEntity(Board board) {
        BoardEntity entity = new BoardEntity();
        entity.setTitle(board.getTitle());
        entity.setDescription(board.getDescription());
        entity.setPublic(board.isPublic());
        if (board.getBackgroundColor() == null) {
            entity.setBackgroundColor(createBackgroundColor());
        } else {
            entity.setBackgroundColor(board.getBackgroundColor());
        }

        return entity;
    }


    private static String createBackgroundColor() {
        final String[] backgroundColors = {
                "#0079BF",  // Blue
                "#D29034",  // Orange
                "#519839",  // Green
                "#B04632",  // Red
                "#89609E",  // Purple
                "#CD5A91",  // Pink
                "#4BBF6B",  // Lime
                "#00AECC",  // Sky
                "#838C91"   // Grey
        };

        return backgroundColors[(random.nextInt(backgroundColors.length))];
    }
}
