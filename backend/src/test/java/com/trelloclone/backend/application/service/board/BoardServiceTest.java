package com.trelloclone.backend.application.service.board;

import com.trelloclone.backend.application.port.in.board.CreateBoardUseCase.CreateBoardCommand;
import com.trelloclone.backend.application.port.out.account.AccountPort;
import com.trelloclone.backend.application.port.out.board.BoardPort;
import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.Account;
import com.trelloclone.backend.domain.model.Board;
import com.trelloclone.backend.domain.model.Id;
import com.trelloclone.backend.domain.validation.BoardValidator;
import io.vavr.control.Either;
import io.vavr.control.Validation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import io.vavr.collection.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {
    @Mock
    private BoardPort boardPort;

    @Mock
    private BoardValidator boardValidator;

    @Mock
    private AccountPort accountPort;

    @InjectMocks
    private BoardService boardService;

    private final String TEST_EMAIL = "test@example.com";
    private final Id TEST_ACCOUNT_ID = Id.of(UUID.randomUUID());


    private void setupSecurity() {
        // 인증 정보 설정
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(authentication.getName()).thenReturn(TEST_EMAIL);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @DisplayName("getBoards: 계정이 존재할 때 보드 목록을 반환해야 함")
    void getBoards_ShouldReturnBoards_WhenAccountExists() {
        setupSecurity();
        // Given
        Account account = Account.builder()
                .id(TEST_ACCOUNT_ID)
                .email(TEST_EMAIL)
                .build();

        Board board1 = Board.builder()
                .id(Id.of(UUID.randomUUID()))
                .title("보드 1")
                .ownerId(TEST_ACCOUNT_ID)
                .build();

        Board board2 = Board.builder()
                .id(Id.of(UUID.randomUUID()))
                .title("보드 2")
                .ownerId(TEST_ACCOUNT_ID)
                .build();

        java.util.List<Board> expectedBoards = java.util.List.of(board1, board2);

        when(accountPort.findAccountByEmail(TEST_EMAIL)).thenReturn(Either.right(account));
        when(boardPort.getBoardsByAccountId(TEST_ACCOUNT_ID)).thenReturn(Either.right(expectedBoards));

        // When
        Either<Failure, Collection<Board>> result = boardService.getBoards(TEST_ACCOUNT_ID);

        // Then
        assertThat(result.isRight()).isTrue();
        assertThat(result.get()).isEqualTo(expectedBoards);
    }

    @Test
    @DisplayName("getBoards: 계정이 존재하지 않을 때 실패를 반환해야 함")
    void getBoards_ShouldReturnFailure_WhenAccountNotFound() {
        setupSecurity();
        // Given
        Failure accountFailure = Failure.ofNotFound("ACCOUNT_NOT_FOUND");

        when(accountPort.findAccountByEmail(TEST_EMAIL)).thenReturn(Either.left(accountFailure));

        // When
        Either<Failure, Collection<Board>> result = boardService.getBoards(TEST_ACCOUNT_ID);

        // Then
        assertThat(result.isLeft()).isTrue();
        assertThat(result.getLeft()).isEqualTo(accountFailure);
    }

    @Test
    @DisplayName("createBoard: 유효한 명령과 계정이 존재할 때 보드를 생성해야 함")
    void createBoard_ShouldCreateBoard_WhenValidCommandAndAccountExists() {
        setupSecurity();
        // Given
        CreateBoardCommand command = CreateBoardCommand.builder()
                .title("새 보드")
                .description("보드 설명")
                .backgroundColor("#FFFFFF")
                .isPublic(true)
                .build();

        Account account = Account.builder()
                .id(TEST_ACCOUNT_ID)
                .email(TEST_EMAIL)
                .build();

        Board expectedBoard = Board.builder()
                .id(Id.of(UUID.randomUUID()))
                .title(command.title())
                .description(command.description())
                .backgroundColor(command.backgroundColor())
                .isPublic(command.isPublic())
                .ownerId(TEST_ACCOUNT_ID)
                .build();

        when(boardValidator.validate(command)).thenReturn(Validation.valid(command));
        when(accountPort.findAccountByEmail(TEST_EMAIL)).thenReturn(Either.right(account));
        when(boardPort.createBoard(any(Board.class))).thenReturn(Either.right(expectedBoard));

        // When
        Either<Failure, Board> result = boardService.createBoard(command);

        // Then
        assertThat(result.isRight()).isTrue();
        assertThat(result.get()).isEqualTo(expectedBoard);
    }

    @Test
    @DisplayName("createBoard: 유효하지 않은 명령이 주어졌을 때 유효성 검증 실패를 반환해야 함")
    void createBoard_ShouldReturnFailure_WhenInvalidCommand() {
        // Given
        CreateBoardCommand command = CreateBoardCommand.builder()
                .title("") // 빈 제목, 유효하지 않음
                .build();
        var violation = Failure.FieldViolation.builder()
                .field("title")
                .message("제목은 비워둘 수 없습니다")
                .rejectedValue("")
                .build();

        when(boardValidator.validate(command))
                .thenReturn(Validation.invalid(List.of(violation)));

        // When
        Either<Failure, Board> result = boardService.createBoard(command);

        // Then
        assertThat(result.isLeft()).isTrue();
        assertThat(result.getLeft().message()).isEqualTo("validation.error");
    }
}
