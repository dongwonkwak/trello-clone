package com.trelloclone.backend.adapter.out.persistence.mapper;

import com.trelloclone.backend.adapter.out.persistence.entity.AccountEntity;
import com.trelloclone.backend.domain.model.Account;
import com.trelloclone.backend.domain.model.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AccountPersistenceMapperTest {

    private final UUID id = UUID.randomUUID();
    private final LocalDateTime now = LocalDateTime.now();
    private final LocalDateTime updated = LocalDateTime.now().plusDays(1);

    @Test
    @DisplayName("Should convert from entity to domain model")
    void shouldConvertFromEntityToDomain() {
        // Given
        AccountEntity entity = new AccountEntity();
        entity.setId(id);
        entity.setFirstName("john");
        entity.setLastName("doe");
        entity.setEmail("test@example.com");
        entity.setPassword("password123");
        entity.setProfileImage("profile.jpg");
        entity.setEmailVerified(true);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(updated);

        // When
        Account account = AccountPersistenceMapper.toDomain(entity);

        // Then
        assertThat(account.getId().id()).isEqualTo(id);
        assertThat(account.getFirstName()).isEqualTo("john");
        assertThat(account.getLastName()).isEqualTo("doe");
        assertThat(account.getEmail()).isEqualTo("test@example.com");
        assertThat(account.getPassword()).isEqualTo("password123");
        assertThat(account.getProfileImageUrl()).isEqualTo("profile.jpg");
        assertThat(account.isEmailVerified()).isTrue();
        assertThat(account.getCreatedAt()).isEqualTo(now);
        assertThat(account.getUpdatedAt()).isEqualTo(updated);
    }

    @Test
    @DisplayName("Should convert from domain model to entity")
    void shouldConvertFromDomainToEntity() {
        // Given
        Account account = Account.builder()
                .id(Id.of(id))
                .firstName("john")
                .lastName("doe")
                .email("test@example.com")
                .password("password123")
                .profileImageUrl("profile.jpg")
                .emailVerified(true)
                .createdAt(now)
                .updatedAt(updated)
                .build();

        // When
        AccountEntity entity = AccountPersistenceMapper.toEntity(account);

        // Then
        assertThat(entity.getId()).isNull();
        assertThat(entity.getFirstName()).isEqualTo("john");
        assertThat(entity.getLastName()).isEqualTo("doe");
        assertThat(entity.getEmail()).isEqualTo("test@example.com");
        assertThat(entity.getPassword()).isEqualTo("password123");
        assertThat(entity.getProfileImage()).isEqualTo("profile.jpg");
        assertThat(entity.isEmailVerified()).isTrue();
    }

    @Test
    @DisplayName("Should handle null optional fields")
    void shouldHandleNullOptionalFields() {
        // Given
        AccountEntity entity = new AccountEntity();
        entity.setId(id);
        entity.setFirstName("john");
        entity.setLastName("doe");
        entity.setEmail("test@example.com");
        entity.setPassword("password123");
        entity.setProfileImage(null);
        entity.setEmailVerified(false);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(updated);

        // When
        Account account = AccountPersistenceMapper.toDomain(entity);
        AccountEntity reconvertedEntity = AccountPersistenceMapper.toEntity(account);

        // Then
        assertThat(account.getProfileImageUrl()).isNull();
        assertThat(reconvertedEntity.getProfileImage()).isNull();
    }
}
