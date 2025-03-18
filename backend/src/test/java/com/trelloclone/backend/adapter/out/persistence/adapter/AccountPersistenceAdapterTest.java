package com.trelloclone.backend.adapter.out.persistence.adapter;

import com.trelloclone.backend.adapter.out.persistence.entity.AccountEntity;
import com.trelloclone.backend.adapter.out.persistence.repository.AccountRepository;
import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.Account;
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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountPersistenceAdapterTest {

    @Mock
    private AccountRepository accountRepository;

    private AccountPersistenceAdapter adapter;
    private final UUID testId = UUID.randomUUID();
    private final Id accountId = Id.of(testId);
    private final AccountEntity accountEntity = new AccountEntity();
    private final Account account = Account.builder()
            .id(accountId)
            .firstName("john")
            .lastName("doe")
            .email("test@example.com")
            .password("password")
            .build();

    @BeforeEach
    void setUp() {
        adapter = new AccountPersistenceAdapter(accountRepository);
        accountEntity.setId(testId);
        accountEntity.setFirstName("john");
        accountEntity.setLastName("doe");
        accountEntity.setEmail("test@example.com");
        accountEntity.setPassword("password");
        accountEntity.setCreatedAt(LocalDateTime.now());
        accountEntity.setUpdatedAt(LocalDateTime.now());
    }

    @Nested
    @DisplayName("Save Account Tests")
    class SaveAccountTests {

        @Test
        @DisplayName("Should save account successfully")
        void shouldSaveAccountSuccessfully() {
            // Given
            when(accountRepository.save(any(AccountEntity.class))).thenReturn(accountEntity);

            // When
            Either<Failure, Account> result = adapter.saveAccount(account);

            // Then
            assertThat(result.isRight()).isTrue();
            assertThat(result.get().getId()).isEqualTo(accountId);
            verify(accountRepository).save(any(AccountEntity.class));
        }

        @Test
        @DisplayName("Should return failure when save throws exception")
        void shouldReturnFailureWhenSaveFails() {
            // Given
            when(accountRepository.save(any(AccountEntity.class))).thenThrow(new DataAccessException("Database error") {
            });

            // When
            Either<Failure, Account> result = adapter.saveAccount(account);

            // Then
            assertThat(result.isLeft()).isTrue();
            assertThat(result.getLeft()).isInstanceOf(Failure.InternalServerError.class);
        }
    }

    @Nested
    @DisplayName("Find Account Tests")
    class FindAccountTests {

        @Test
        @DisplayName("Should find account by ID successfully")
        void shouldFindAccountByIdSuccessfully() {
            // Given
            when(accountRepository.findById(testId)).thenReturn(Optional.of(accountEntity));

            // When
            Either<Failure, Account> result = adapter.findAccountById(accountId);

            // Then
            assertThat(result.isRight()).isTrue();
            assertThat(result.get().getId()).isEqualTo(accountId);
        }

        @Test
        @DisplayName("Should return not found when account ID doesn't exist")
        void shouldReturnNotFoundWhenAccountIdDoesntExist() {
            // Given
            when(accountRepository.findById(testId)).thenReturn(Optional.empty());

            // When
            Either<Failure, Account> result = adapter.findAccountById(accountId);

            // Then
            assertThat(result.isLeft()).isTrue();
            assertThat(result.getLeft()).isInstanceOf(Failure.NotFoundFailure.class);
        }

        @Test
        @DisplayName("Should return failure when findById throws exception")
        void shouldReturnFailureWhenFindByIdThrowsException() {
            // Given
            when(accountRepository.findById(testId)).thenThrow(new RuntimeException("Database error"));

            // When
            Either<Failure, Account> result = adapter.findAccountById(accountId);

            // Then
            assertThat(result.isLeft()).isTrue();
            assertThat(result.getLeft()).isInstanceOf(Failure.InternalServerError.class);
        }

        @Test
        @DisplayName("Should find account by email successfully")
        void shouldFindAccountByEmailSuccessfully() {
            // Given
            when(accountRepository.findByEmail("test@example.com")).thenReturn(Optional.of(accountEntity));

            // When
            Either<Failure, Account> result = adapter.findAccountByEmail("test@example.com");

            // Then
            assertThat(result.isRight()).isTrue();
            assertThat(result.get().getEmail()).isEqualTo("test@example.com");
        }
    }

    @Nested
    @DisplayName("Account Existence Tests")
    class AccountExistenceTests {

        @Test
        @DisplayName("Should check if account exists by email")
        void shouldCheckIfAccountExistsByEmail() {
            // Given
            when(accountRepository.existsByEmail("test@example.com")).thenReturn(true);
            when(accountRepository.existsByEmail("nonexistent@example.com")).thenReturn(false);

            // When & Then
            assertThat(adapter.existsByEmail("test@example.com")).isTrue();
            assertThat(adapter.existsByEmail("nonexistent@example.com")).isFalse();
        }
    }

    @Test
    @DisplayName("Should delete account by ID")
    void shouldDeleteAccountById() {
        // When
        adapter.deleteAccount(accountId);

        // Then
        verify(accountRepository).deleteById(testId);
    }
}
