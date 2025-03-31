package com.trelloclone.backend.adapter.out.persistence.adapter;

import com.trelloclone.backend.adapter.out.persistence.entity.AccountEntity;
import com.trelloclone.backend.adapter.out.persistence.entity.BoardEntity;
import com.trelloclone.backend.adapter.out.persistence.repository.BoardMemberRepository;
import com.trelloclone.backend.adapter.out.persistence.repository.BoardRepository;
import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.Board;
import com.trelloclone.backend.domain.model.Id;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardPersistenceAdapterTest {

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private BoardMemberRepository boardMemberRepository;

    private BoardPersistenceAdapter adapter;
    private final UUID testBoardId = UUID.randomUUID();
    private final UUID testOwnerId = UUID.randomUUID();
    private final Id boardId = Id.of(testBoardId);
    private final Id ownerId = Id.of(testOwnerId);
    private final AccountEntity ownerEntity = new AccountEntity();
    private final BoardEntity boardEntity = new BoardEntity();
    private final Board board = Board.builder()
            .id(boardId)
            .title("테스트 보드")
            .description("테스트 설명")
            .build();

    @BeforeEach
    void setUp() {
        adapter = new BoardPersistenceAdapter(boardRepository, boardMemberRepository);
        ownerEntity.setId(testOwnerId);
        boardEntity.setId(testBoardId);
        boardEntity.setTitle("테스트 보드");
        boardEntity.setDescription("테스트 설명");
        boardEntity.setOwner(ownerEntity);
        boardEntity.setCreatedAt(LocalDateTime.now());
        boardEntity.setUpdatedAt(LocalDateTime.now());
    }

    @Nested
    @DisplayName("Create Board Tests")
    class CreateBoardTests {

        @Test
        @DisplayName("보드 생성 성공 테스트")
        void shouldCreateBoardSuccessfully() {
            // Given
            when(boardRepository.save(any(BoardEntity.class))).thenReturn(boardEntity);

            // When
            Either<Failure, Board> result = adapter.createBoard(board, ownerId);

            // Then
            assertThat(result.isRight()).isTrue();
            assertThat(result.get().getId()).isEqualTo(boardId);
            verify(boardRepository).save(any(BoardEntity.class));
        }

        @Test
        @DisplayName("보드 생성 실패 시 Failure 반환")
        void shouldReturnFailureWhenCreateBoardFails() {
            // Given
            when(boardRepository.save(any(BoardEntity.class))).thenThrow(new DataAccessException("Database error") {});

            // When
            Either<Failure, Board> result = adapter.createBoard(board, ownerId);

            // Then
            assertThat(result.isLeft()).isTrue();
            assertThat(result.getLeft()).isInstanceOf(Failure.InternalServerError.class);
        }
    }

    @Nested
    @DisplayName("Update Board Tests")
    class UpdateBoardTests {

        @Test
        @DisplayName("보드 업데이트 성공 테스트")
        void shouldUpdateBoardSuccessfully() {
            // Given
            when(boardRepository.findById(testBoardId)).thenReturn(Optional.of(boardEntity));
            when(boardRepository.save(any(BoardEntity.class))).thenReturn(boardEntity);

            // When
            Either<Failure, Board> result = adapter.updateBoard(board);

            // Then
            assertThat(result.isRight()).isTrue();
            assertThat(result.get().getId()).isEqualTo(boardId);
            verify(boardRepository).save(any(BoardEntity.class));
        }

        @Test
        @DisplayName("보드가 존재하지 않을 때 NotFoundFailure 반환")
        void shouldReturnNotFoundWhenBoardDoesntExist() {
            // Given
            when(boardRepository.findById(testBoardId)).thenReturn(Optional.empty());

            // When
            Either<Failure, Board> result = adapter.updateBoard(board);

            // Then
            assertThat(result.isLeft()).isTrue();
            assertThat(result.getLeft()).isInstanceOf(Failure.NotFoundFailure.class);
        }
    }

    @Nested
    @DisplayName("Get Boards By Account ID Tests")
    class GetBoardsByAccountIdTests {

        @Test
        @DisplayName("계정 ID로 보드 목록 조회 성공 테스트")
        void shouldGetBoardsByAccountIdSuccessfully() {
            // Given
            List<BoardEntity> boardEntities = List.of(boardEntity);
            when(boardRepository.findAllBoardsByAccount(any(AccountEntity.class))).thenReturn(boardEntities);

            // When
            Either<Failure, List<Board>> result = adapter.getBoardsByAccountId(ownerId);

            // Then
            assertThat(result.isRight()).isTrue();
            assertThat(result.get()).hasSize(1);
            assertThat(result.get().getFirst().getId()).isEqualTo(boardId);
        }

        @Test
        @DisplayName("조회 실패 시 Failure 반환")
        void shouldReturnFailureWhenGetBoardsFails() {
            // Given
            when(boardRepository.findAllBoardsByAccount(ownerEntity))
                    .thenThrow(new RuntimeException("Database error"));

            // When
            Either<Failure, List<Board>> result = adapter.getBoardsByAccountId(ownerId);

            // Then
            assertThat(result.isLeft()).isTrue();
            assertThat(result.getLeft()).isInstanceOf(Failure.InternalServerError.class);
        }
    }

    @Test
    @DisplayName("보드 삭제 테스트")
    void shouldDeleteBoardById() {
        // When
        Either<Failure, Void> result = adapter.deleteBoard(boardId);

        // Then
        assertThat(result.isRight()).isTrue();
        verify(boardRepository).deleteById(testBoardId);
    }
}
