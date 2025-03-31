package com.trelloclone.backend.application.port.in.board;

import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.Board;
import com.trelloclone.backend.domain.model.Id;
import io.vavr.control.Either;

import java.util.Collection;

public interface GetBoardUseCase {

    Either<Failure, Collection<Board>> getBoards(Id accountId);

}
