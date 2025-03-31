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

import java.util.Collection;

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
    public Either<Failure, Collection<Board>> getBoards(Id accountId) {
        // Get current user
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return accountPort.findAccountByEmail(email)
                .flatMap(account -> boardPort.getBoardsByAccountId(account.getId()));
    }

    @Override
    public Either<Failure, Board> createBoard(CreateBoardCommand command) {
        var validation = boardValidator.validate(command);
        if (validation.isInvalid()) {
            log.error("Validation failed: {}", validation.getError());
            return Either.left(Failure.ofValidation(
                    ValidationMessageKeys.VALIDATION_ERROR,
                    validation.getError().toJavaList()));
        }

        // Get current user
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return accountPort.findAccountByEmail(email)
                .flatMap(account -> {
                    var newBoard = Board.builder()
                            .title(command.title())
                            .description(command.description())
                            .backgroundColor(command.backgroundColor())
                            .isPublic(command.isPublic())
                            .build();
                    return boardPort.createBoard(newBoard, account.getId());
                });
    }
}
