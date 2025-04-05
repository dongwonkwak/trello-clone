package com.trelloclone.backend.application.port.in.board;

import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.Board;
import io.vavr.control.Either;

import java.util.List;

public interface GetBoardUseCase {

    Either<Failure, List<Board>> getBoards(String email);

}
