package com.trelloclone.backend.adapter.in.web.mapper;


import com.trelloclone.backend.adapter.in.web.model.BoardResponse;
import com.trelloclone.backend.adapter.in.web.model.BoardsResponse;
import com.trelloclone.backend.domain.model.Board;

import java.util.List;

public class BoardMapper {

    private BoardMapper() {

    }

    public static BoardResponse toResponse(Board board) {
        return new BoardResponse()
                .id(board.getId().id())
                .ownerId(board.getOwnerId().id())
                .title(board.getTitle())
                .description(board.getDescription())
                .isPublic(board.isPublic())
                .backgroundColor(board.getBackgroundColor())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt());
    }

    public static BoardsResponse toResponse(List<Board> boards) {
        return new BoardsResponse()
                .boards(boards.stream()
                        .map(BoardMapper::toResponse)
                        .toList());
    }
}
