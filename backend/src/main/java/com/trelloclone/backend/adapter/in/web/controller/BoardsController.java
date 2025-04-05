package com.trelloclone.backend.adapter.in.web.controller;

import com.trelloclone.backend.adapter.in.web.api.BoardApi;
import com.trelloclone.backend.adapter.in.web.common.ApiFailureHandler;
import com.trelloclone.backend.adapter.in.web.mapper.BoardMapper;
import com.trelloclone.backend.adapter.in.web.model.CreateBoardRequest;
import com.trelloclone.backend.application.port.in.board.CreateBoardUseCase;
import com.trelloclone.backend.application.port.in.board.GetBoardUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BoardsController implements BoardApi {

    private final GetBoardUseCase getBoardUseCase;
    private final CreateBoardUseCase createBoardUseCase;
    private final ApiFailureHandler apiFailureHandler;

    @Override
    @PostMapping("/v1/boards")
    public ResponseEntity<?> createBoard(CreateBoardRequest createBoardRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        var command = CreateBoardUseCase.CreateBoardCommand.builder()
                .title(createBoardRequest.getTitle())
                .description(createBoardRequest.getDescription())
                .backgroundColor(createBoardRequest.getBackgroundColor())
                .isPublic(createBoardRequest.getIsPublic())
                .build();

        return createBoardUseCase.createBoard(command, email)
                .fold(apiFailureHandler::handle,
                        board -> ResponseEntity.ok(BoardMapper.toResponse(board)));

    }

    @Override
    @GetMapping("/v1/boards")
    public ResponseEntity<?> getBoards() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getBoardUseCase.getBoards(email)
                .fold(apiFailureHandler::handle,
                        boards -> ResponseEntity.ok(BoardMapper.toResponse(boards)));
    }
}
