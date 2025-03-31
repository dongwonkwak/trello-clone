package com.trelloclone.backend.adapter.out.persistence.mapper;

import com.trelloclone.backend.adapter.out.persistence.entity.AccountEntity;
import com.trelloclone.backend.adapter.out.persistence.entity.BoardEntity;
import com.trelloclone.backend.domain.model.Board;
import com.trelloclone.backend.domain.model.Id;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BoardPersistenceMapperTest {

    private final UUID id = UUID.randomUUID();
    private final UUID ownerId = UUID.randomUUID();
    private final AccountEntity owner = new AccountEntity();
    private final LocalDateTime now = LocalDateTime.now();
    private final LocalDateTime updated = LocalDateTime.now().plusDays(1);

    @BeforeEach
    void setUp() {
        owner.setId(ownerId);
    }

    @Test
    @DisplayName("엔티티에서 도메인 모델로 변환")
    void shouldConvertFromEntityToDomain() {
        // Given
        BoardEntity entity = new BoardEntity();
        entity.setId(id);
        entity.setTitle("프로젝트 보드");
        entity.setDescription("프로젝트 관리를 위한 보드");
        entity.setBackgroundColor("#FF5733");
        entity.setPublic(true);
        entity.setOwnerId(ownerId);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(updated);

        // When
        Board board = BoardPersistenceMapper.toDomain(entity);

        // Then
        assertThat(board.getId().id()).isEqualTo(id);
        assertThat(board.getTitle()).isEqualTo("프로젝트 보드");
        assertThat(board.getDescription()).isEqualTo("프로젝트 관리를 위한 보드");
        assertThat(board.getBackgroundColor()).isEqualTo("#FF5733");
        assertThat(board.isPublic()).isTrue();
        assertThat(board.getCreatedAt()).isEqualTo(now);
        assertThat(board.getUpdatedAt()).isEqualTo(updated);
    }

    @Test
    @DisplayName("도메인 모델에서 엔티티로 변환")
    void shouldConvertFromDomainToEntity() {
        // Given
        Board board = Board.builder()
                .id(Id.of(id))
                .ownerId(Id.of(ownerId))
                .title("프로젝트 보드")
                .description("프로젝트 관리를 위한 보드")
                .backgroundColor("#FF5733")
                .isPublic(true)
                .createdAt(now)
                .updatedAt(updated)
                .build();

        // When
        BoardEntity entity = BoardPersistenceMapper.toEntity(board);

        // Then
        assertThat(entity.getTitle()).isEqualTo("프로젝트 보드");
        assertThat(entity.getDescription()).isEqualTo("프로젝트 관리를 위한 보드");
        assertThat(entity.getBackgroundColor()).isEqualTo("#FF5733");
        assertThat(entity.isPublic()).isTrue();
    }

    @Test
    @DisplayName("선택적 필드가 null인 경우 처리")
    void shouldHandleNullOptionalFields() {
        // Given
        BoardEntity entity = new BoardEntity();
        entity.setId(id);
        entity.setOwnerId(ownerId);
        entity.setTitle("프로젝트 보드");
        entity.setDescription(null);
        entity.setBackgroundColor(null);
        entity.setPublic(false);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(updated);

        // When
        Board board = BoardPersistenceMapper.toDomain(entity);
        BoardEntity reconvertedEntity = BoardPersistenceMapper.toEntity(board);

        // Then
        assertThat(board.getDescription()).isNull();
        assertThat(board.getBackgroundColor()).isNull();
        assertThat(reconvertedEntity.getDescription()).isNull();
        assertThat(reconvertedEntity.getBackgroundColor()).isNotNull();
    }
}
