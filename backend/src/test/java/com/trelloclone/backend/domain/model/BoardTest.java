package com.trelloclone.backend.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Test
    @DisplayName("Should create board using builder with default ID and timestamps")
    void shouldCreateBoardWithDefaultValues() {
        // given
        String title = "Test Board";
        String description = "Test Description";
        boolean isPublic = true;
        String backgroundColor = "#FF0000";
        Account owner = createTestAccount();

        // when
        Board board = Board.builder()
                .title(title)
                .description(description)
                .isPublic(isPublic)
                .backgroundColor(backgroundColor)
                .owner(owner)
                .build();

        // then
        assertThat(board.getId()).isNotNull();
        assertThat(board.getTitle()).isEqualTo(title);
        assertThat(board.getDescription()).isEqualTo(description);
        assertThat(board.isPublic()).isEqualTo(isPublic);
        assertThat(board.getBackgroundColor()).isEqualTo(backgroundColor);
        assertThat(board.getOwner()).isEqualTo(owner);
        assertThat(board.getCreatedAt()).isNotNull();
        assertThat(board.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("Should create board with custom ID")
    void shouldCreateBoardWithCustomId() {
        // given
        Id customId = Id.of(UUID.randomUUID());

        // when
        Board board = Board.builder()
                .id(customId)
                .title("Test Board")
                .owner(createTestAccount())
                .build();

        // then
        assertThat(board.getId()).isEqualTo(customId);
    }

    @Test
    @DisplayName("Should create board with custom timestamps")
    void shouldCreateBoardWithCustomTimestamps() {
        // given
        LocalDateTime createdAt = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime updatedAt = LocalDateTime.of(2023, 1, 2, 0, 0);

        // when
        Board board = Board.builder()
                .title("Test Board")
                .owner(createTestAccount())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        // then
        assertThat(board.getCreatedAt()).isEqualTo(createdAt);
        assertThat(board.getUpdatedAt()).isEqualTo(updatedAt);
    }

    @Test
    @DisplayName("Should copy properties when using toBuilder and update the updatedAt")
    void shouldCopyPropertiesWithToBuilder() {
        // given
        Board originalBoard = Board.builder()
                .title("Original Title")
                .description("Original Description")
                .isPublic(true)
                .backgroundColor("#FF0000")
                .owner(createTestAccount())
                .build();

        LocalDateTime beforeUpdate = LocalDateTime.now();

        // when
        Board updatedBoard = originalBoard.toBuilder()
                .title("Updated Title")
                .build();

        // then
        assertThat(updatedBoard.getId()).isEqualTo(originalBoard.getId());
        assertThat(updatedBoard.getTitle()).isEqualTo("Updated Title");
        assertThat(updatedBoard.getDescription()).isEqualTo(originalBoard.getDescription());
        assertThat(updatedBoard.isPublic()).isEqualTo(originalBoard.isPublic());
        assertThat(updatedBoard.getBackgroundColor()).isEqualTo(originalBoard.getBackgroundColor());
        assertThat(updatedBoard.getOwner()).isEqualTo(originalBoard.getOwner());
        assertThat(updatedBoard.getCreatedAt()).isEqualTo(originalBoard.getCreatedAt());
        assertThat(updatedBoard.getUpdatedAt()).isAfter(originalBoard.getUpdatedAt());
        assertThat(updatedBoard.getUpdatedAt()).isAfterOrEqualTo(beforeUpdate);
    }

    @Test
    @DisplayName("Should be equal when IDs are the same")
    void shouldBeEqualBasedOnId() {
        // given
        Id sharedId = Id.of(UUID.randomUUID());
        Account owner = createTestAccount();

        Board board1 = Board.builder()
                .id(sharedId)
                .title("Title 1")
                .owner(owner)
                .build();

        Board board2 = Board.builder()
                .id(sharedId)
                .title("Different Title")
                .owner(owner)
                .build();

        Board board3 = Board.builder()
                .title("Title 1")
                .owner(owner)
                .build();

        // then
        assertThat(board1).isEqualTo(board2);
        assertThat(board1).isNotEqualTo(board3);
        assertThat(board1.hashCode()).isEqualTo(board2.hashCode());
    }

    private Account createTestAccount() {
        // Create a simple test account
        return Account.builder().build();
    }
}
