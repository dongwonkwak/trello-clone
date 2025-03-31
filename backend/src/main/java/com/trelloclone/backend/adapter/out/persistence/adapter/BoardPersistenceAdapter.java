package com.trelloclone.backend.adapter.out.persistence.adapter;

import com.trelloclone.backend.adapter.out.persistence.entity.AccountEntity;
import com.trelloclone.backend.adapter.out.persistence.entity.BoardEntity;
import com.trelloclone.backend.adapter.out.persistence.entity.BoardMemberEntity;
import com.trelloclone.backend.adapter.out.persistence.mapper.BoardPersistenceMapper;
import com.trelloclone.backend.adapter.out.persistence.repository.BoardMemberRepository;
import com.trelloclone.backend.adapter.out.persistence.repository.BoardRepository;
import com.trelloclone.backend.application.port.out.board.BoardPort;
import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.Board;
import com.trelloclone.backend.domain.model.BoardRole;
import com.trelloclone.backend.domain.model.Id;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoardPersistenceAdapter implements BoardPort {
    private final BoardRepository boardRepository;
    private final BoardMemberRepository boardMemberRepository;

    @Override
    public Either<Failure, Board> createBoard(Board board) {
        return Try.of(() -> {
                    // 1. 보드 엔티티 생성 및 저장
                    BoardEntity boardEntity = BoardPersistenceMapper.toEntity(board);
                    BoardEntity savedBoardEntity = boardRepository.save(boardEntity);

                    // 2. 보드 멤버 엔티티 생성 및 저장 (보드 생성자를 ADMIN 권한으로)
                    BoardMemberEntity memberEntity = new BoardMemberEntity();
                    memberEntity.setBoard(savedBoardEntity);
                    memberEntity.setAccountId(board.getOwnerId().id());

                    memberEntity.setRole(BoardRole.ADMIN); // 보드 생성자는 ADMIN 권한
                    boardMemberRepository.save(memberEntity);

                    // 저장된 보드 엔티티를 도메인 객체로 변환하여 반환
                    return BoardPersistenceMapper.toDomain(savedBoardEntity);
                })
                .toEither()
                .mapLeft(ex -> {
                    log.error("Failed to create board: {}", ex.getMessage());
                    return Failure.ofInternalServerError("Failed to create board: " + ex.getMessage());
                });
    }

    @Override
    public Either<Failure, Board> updateBoard(Board board) {
        return Try.of(() ->
             boardRepository.findById(board.getId().id())
                    .map(entity -> {
                        entity.setTitle(board.getTitle());
                        entity.setDescription(board.getDescription());
                        var updatedBoardEntity = boardRepository.save(entity);
                        return Either.<Failure, Board>right(
                                BoardPersistenceMapper.toDomain(updatedBoardEntity)
                        );
                    })
                    .orElseGet(() -> Either.left(
                            Failure.ofNotFound("Board not found")))
        )
        .getOrElseGet(throwable ->
                Either.left(
                        Failure.ofInternalServerError("Failed to update board: " + throwable.getMessage())
                ));
    }


    @Override
    public Either<Failure, Board> findBoardById(Id boardId) {
        return Try.of(() -> {
            var boardEntity = boardRepository.findById(boardId.id());
            return boardEntity.map(entity -> Either.<Failure, Board>right(
                    BoardPersistenceMapper.toDomain(entity)
            )).orElseGet(() -> Either.left(
                    Failure.ofNotFound("Board not found")));

            })
        .getOrElseGet(throwable ->
            Either.left(
                Failure.ofInternalServerError("Failed to retrieve board: " + throwable.getMessage())
            )
        );
    }

    @Override
    public Either<Failure, List<Board>> getBoardsByAccountId(Id accountId) {
        return Try.of(() -> {
            // find account entity by id
            var boardEntities = boardRepository.findAllBoardsByAccount(accountId.id());

            return Either.<Failure, List<Board>>right(
                    boardEntities.stream()
                            .map(BoardPersistenceMapper::toDomain)
                            .toList()
            );
        })
        .getOrElseGet(throwable ->
            Either.left(
                Failure.ofInternalServerError("Failed to retrieve boards: " + throwable.getMessage())
        ));
    }

    @Override
    public Either<Failure, Void> deleteBoard(Id boardId) {
        return Try.of(() -> {
            // delete board members
            boardMemberRepository.deleteByBoardId(boardId.id());
            // delete board itself
            boardRepository.deleteById(boardId.id());

            return Either.<Failure, Void>right(null);
        })
        .getOrElseGet(throwable ->
             Either.left(
                     Failure.ofInternalServerError("Failed to delete board: " + throwable.getMessage()))
        );
    }
}
