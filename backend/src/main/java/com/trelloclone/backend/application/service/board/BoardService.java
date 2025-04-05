package com.trelloclone.backend.application.service.board;

import com.trelloclone.backend.application.port.in.board.CreateBoardUseCase;
import com.trelloclone.backend.application.port.in.board.GetBoardUseCase;
import com.trelloclone.backend.application.port.out.account.AccountPort;
import com.trelloclone.backend.application.port.out.board.BoardPort;
import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.Board;
import com.trelloclone.backend.domain.model.Id;
import com.trelloclone.backend.domain.validation.BoardValidator;
import com.trelloclone.backend.domain.validation.ValidationMessageKeys;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BoardService implements
        GetBoardUseCase,
        CreateBoardUseCase {

    private final BoardPort boardPort;
    private final BoardValidator boardValidator;
    private final AccountPort accountPort;

    @Override
    public Either<Failure, List<Board>> getBoards(String email) {
        return accountPort.findAccountByEmail(email)
                .flatMap(account -> boardPort.getBoardsByAccountId(account.getId()));
    }

    @Override
    public Either<Failure, Board> createBoard(CreateBoardCommand command, String email) {
        var validation = boardValidator.validate(command);
        if (validation.isInvalid()) {
            log.error("Validation failed: {}", validation.getError());
            return Either.left(Failure.ofValidation(
                    ValidationMessageKeys.VALIDATION_ERROR,
                    validation.getError().toJavaList()));
        }

        return accountPort.findAccountByEmail(email)
                .flatMap(account -> {
                    var newBoard = Board.builder()
                            .title(command.title())
                            .description(command.description())
                            .backgroundColor(command.backgroundColor())
                            .isPublic(command.isPublic())
                            .ownerId(account.getId())
                            .build();
                    return boardPort.createBoard(newBoard);
                });
    }
}
