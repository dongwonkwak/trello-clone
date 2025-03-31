package com.trelloclone.backend.application.port.out.board;

import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.Board;
import com.trelloclone.backend.domain.model.Id;
import io.vavr.control.Either;

import java.util.List;

public interface BoardPort {

    /**
     * 새 보드를 생성합니다.
     * 보드와 보드 멤버(소유자)를 함께 생성합니다.
     *
     * @param board 생성할 보드
     * @return 생성된 보드
     */
    Either<Failure, Board> createBoard(Board board);

    /**
     * 기존 보드를 업데이트합니다.
     * 보드 정보만 업데이트하고 멤버십은 변경하지 않습니다.
     *
     * @param board 업데이트할 보드
     * @return 업데이트된 보드
     */
    Either<Failure, Board> updateBoard(Board board);

    /**
     * ID로 보드를 조회합니다.
     *
     * @param boardId 보드 ID
     * @return 보드 정보 (존재하지 않으면 Failure.NotFoundFailure)
     */
    Either<Failure, Board> findBoardById(Id boardId);
    /**
     * 사용자의 모든 보드를 조회합니다.
     * 사용자가 소유한 보드와 사용자가 멤버로 추가된 보드를 모두 포함합니다.
     *
     * @param accountId 사용자 ID
     * @return 사용자의 보드 목록
     */
    Either<Failure, List<Board>> getBoardsByAccountId(Id accountId);

    /**
     * 보드를 삭제합니다.
     *
     * @param boardId 보드 ID
     */
    Either<Failure, Void> deleteBoard(Id boardId);
}
