package com.trelloclone.backend.adaptor.out.token;

import com.trelloclone.backend.adapter.out.token.InMemoryTokenAdapter;
import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.account.AccountId;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class InMemoryTokenAdapterTest {

    private InMemoryTokenAdapter tokenAdapter;
    private final UUID testId = UUID.randomUUID();
    private final AccountId accountId = new AccountId(testId);

    @BeforeEach
    void setUp() {
        tokenAdapter = new InMemoryTokenAdapter();
    }

    @Test
    @DisplayName("Should create activation token successfully")
    void shouldCreateActivationTokenSuccessfully() {
        // When
        Either<Failure, String> result = tokenAdapter.createActivationToken(accountId, LocalDateTime.now().plusHours(24));

        // Then
        assertThat(result.isRight()).isTrue();
        assertThat(result.get()).isNotBlank();
    }

    @Test
    @DisplayName("Should validate token successfully")
    void shouldValidateTokenSuccessfully() {
        // Given
        String token = tokenAdapter.createActivationToken(accountId, LocalDateTime.now().plusHours(24)).get();

        // When
        Either<Failure, AccountId> result = tokenAdapter.validateActivationToken(token);

        // Then
        assertThat(result.isRight()).isTrue();
        assertThat(result.get()).isEqualTo(accountId);

        // Verify token was removed after validation
        Either<Failure, AccountId> secondValidation = tokenAdapter.validateActivationToken(token);
        assertThat(secondValidation.isLeft()).isTrue();
        assertThat(secondValidation.getLeft().message()).contains("Invalid activation token");
    }

    @Test
    @DisplayName("Should reject invalid token")
    void shouldRejectInvalidToken() {
        // When
        Either<Failure, AccountId> result = tokenAdapter.validateActivationToken("invalid-token");

        // Then
        assertThat(result.isLeft()).isTrue();
        assertThat(result.getLeft()).isInstanceOf(Failure.IllegalFailure.class);
        assertThat(result.getLeft().message()).contains("Invalid activation token");
    }

    @Test
    @DisplayName("Should reject expired token")
    void shouldRejectExpiredToken() throws Exception {
        // Given
        String token = tokenAdapter.createActivationToken(accountId, LocalDateTime.now().minusHours(1)).get();

        // When
        Either<Failure, AccountId> result = tokenAdapter.validateActivationToken(token);

        // Then
        assertThat(result.isLeft()).isTrue();
        assertThat(result.getLeft()).isInstanceOf(Failure.IllegalFailure.class);
        assertThat(result.getLeft().message()).contains("expired");
    }
}
