package com.trelloclone.backend.application.service.account;


import com.trelloclone.backend.application.port.in.account.CreateAccountUseCase;
import com.trelloclone.backend.application.port.in.account.UpdateAccountUseCase;
import com.trelloclone.backend.application.port.out.account.AccountPort;
import com.trelloclone.backend.application.port.out.token.TokenPort;
import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.domain.AccountCreatedEvent;
import com.trelloclone.backend.domain.model.account.Account;
import com.trelloclone.backend.domain.model.account.AccountId;
import com.trelloclone.backend.domain.validation.AccountValidator;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountPort accountPort;

    @Mock
    private AccountValidator accountValidator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenPort tokenPort;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private AccountService accountService;

    private final UUID testId = UUID.randomUUID();
    private final AccountId accountId = new AccountId(testId);
    private Account testAccount;

    @BeforeEach
    void setUp() {
        accountService = new AccountService(
                accountPort, accountValidator, passwordEncoder, tokenPort, eventPublisher
        );

        testAccount =  new Account(
                accountId,
                "testuser",
                "test@example.com",
                "encoded_password",
                "Test User",
                null,
                false,
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    @Nested
    @DisplayName("Create Account Tests")
    class CreateAccountTests {

        private final CreateAccountUseCase.CreateAccountCommand validCommand = new CreateAccountUseCase.CreateAccountCommand(
                "testuser", "test@example.com", "Password123", null, null
        );

        @Test
        @DisplayName("Should create account successfully")
        void shouldCreateAccountSuccessfully() {
            // Given
            when(accountValidator.validate(any(CreateAccountUseCase.CreateAccountCommand.class)))
                    .thenReturn(Validation.valid(validCommand));
            when(accountPort.existsByEmail(anyString())).thenReturn(false);
            when(accountPort.existsByUsername(anyString())).thenReturn(false);
            when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
            when(accountPort.saveAccount(any(Account.class))).thenReturn(Either.right(testAccount));
            when(tokenPort.createActivationToken(any(AccountId.class), any(LocalDateTime.class))).thenReturn(Either.right("token"));

            // When
            Either<Failure, Account> result = accountService.createAccount(validCommand);

            // Then
            assertThat(result.isRight()).isTrue();
            verify(eventPublisher).publishEvent(any(AccountCreatedEvent.class));
            verify(accountPort).saveAccount(any(Account.class));
        }

        @Test
        @DisplayName("Should reject when validation fails")
        void shouldRejectWhenValidationFails() {
            // Given
            Failure.FieldViolation violation = Failure.FieldViolation.builder()
                    .field("email")
                    .message("Invalid email")
                    .build();

            when(accountValidator.validate(any(CreateAccountUseCase.CreateAccountCommand.class)))
                    .thenReturn(Validation.invalid(List.of(violation)));

            // When
            Either<Failure, Account> result = accountService.createAccount(validCommand);

            // Then
            assertThat(result.isLeft()).isTrue();
            assertThat(result.getLeft()).isInstanceOf(Failure.ValidationFailure.class);
            verify(accountPort, never()).saveAccount(any());
        }

        @Test
        @DisplayName("Should reject when email already exists")
        void shouldRejectWhenEmailAlreadyExists() {
            // Given
            when(accountValidator.validate(any(CreateAccountUseCase.CreateAccountCommand.class)))
                    .thenReturn(Validation.valid(validCommand));
            when(accountPort.existsByEmail(anyString())).thenReturn(true);

            // When
            Either<Failure, Account> result = accountService.createAccount(validCommand);

            // Then
            assertThat(result.isLeft()).isTrue();
            assertThat(result.getLeft()).isInstanceOf(Failure.ConflictFailure.class);
            verify(accountPort, never()).saveAccount(any());
        }

        @Test
        @DisplayName("Should reject when username already exists")
        void shouldRejectWhenUsernameAlreadyExists() {
            // Given
            when(accountValidator.validate(any(CreateAccountUseCase.CreateAccountCommand.class)))
                    .thenReturn(Validation.valid(validCommand));
            when(accountPort.existsByEmail(anyString())).thenReturn(false);
            when(accountPort.existsByUsername(anyString())).thenReturn(true);

            // When
            Either<Failure, Account> result = accountService.createAccount(validCommand);

            // Then
            assertThat(result.isLeft()).isTrue();
            assertThat(result.getLeft()).isInstanceOf(Failure.ConflictFailure.class);
            verify(accountPort, never()).saveAccount(any());
        }
    }

    @Nested
    @DisplayName("Get Account Tests")
    class GetAccountTests {

        @Test
        @DisplayName("Should get account by ID")
        void shouldGetAccountById() {
            // Given
            when(accountPort.findAccountById(accountId)).thenReturn(Either.right(testAccount));

            // When
            Either<Failure, Account> result = accountService.getAccountById(accountId);

            // Then
            assertThat(result.isRight()).isTrue();
            assertThat(result.get()).isEqualTo(testAccount);
        }

        @Test
        @DisplayName("Should return failure when account not found by ID")
        void shouldReturnFailureWhenAccountNotFoundById() {
            // Given
            Failure notFoundFailure = Failure.ofNotFound("Account not found");
            when(accountPort.findAccountById(accountId)).thenReturn(Either.left(notFoundFailure));

            // When
            Either<Failure, Account> result = accountService.getAccountById(accountId);

            // Then
            assertThat(result.isLeft()).isTrue();
            assertThat(result.getLeft()).isInstanceOf(Failure.NotFoundFailure.class);
        }

        @Test
        @DisplayName("Should get account by email")
        void shouldGetAccountByEmail() {
            // Given
            when(accountPort.findAccountByEmail("test@example.com")).thenReturn(Either.right(testAccount));

            // When
            Either<Failure, Account> result = accountService.getAccountByEmail("test@example.com");

            // Then
            assertThat(result.isRight()).isTrue();
            assertThat(result.get()).isEqualTo(testAccount);
        }

        @Test
        @DisplayName("Should get account by username")
        void shouldGetAccountByUsername() {
            // Given
            when(accountPort.findAccountByUsername("testuser")).thenReturn(Either.right(testAccount));

            // When
            Either<Failure, Account> result = accountService.getAccountByUsername("testuser");

            // Then
            assertThat(result.isRight()).isTrue();
            assertThat(result.get()).isEqualTo(testAccount);
        }
    }

    @Nested
    @DisplayName("Update Account Tests")
    class UpdateAccountTests {

        @Test
        @DisplayName("Should update account profile")
        void shouldUpdateAccountProfile() {
            // Given
            UpdateAccountUseCase.UpdateAccountCommand command = new UpdateAccountUseCase.UpdateAccountCommand(accountId, "New Name", "image.jpg");
            Account updatedAccount = testAccount;
            updatedAccount.updateProfile("New Name", "image.jpg");

            when(accountPort.findAccountById(accountId)).thenReturn(Either.right(testAccount));
            when(accountPort.saveAccount(any(Account.class))).thenReturn(Either.right(updatedAccount));

            // When
            Either<Failure, Account> result = accountService.updateAccount(command);

            // Then
            assertThat(result.isRight()).isTrue();

            ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
            verify(accountPort).saveAccount(accountCaptor.capture());

            Account capturedAccount = accountCaptor.getValue();
            assertThat(capturedAccount.getFullName()).isEqualTo("New Name");
            assertThat(capturedAccount.getProfileImageUrl()).isEqualTo("image.jpg");
        }

        @Test
        @DisplayName("Should verify email")
        void shouldVerifyEmail() {
            // Given
            testAccount.verifyEmail();
            Account verifiedAccount = testAccount;

            when(accountPort.findAccountById(accountId)).thenReturn(Either.right(testAccount));
            when(accountPort.saveAccount(any(Account.class))).thenReturn(Either.right(verifiedAccount));

            // When
            Either<Failure, Account> result = accountService.verifyEmail(accountId);

            // Then
            assertThat(result.isRight()).isTrue();

            ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
            verify(accountPort).saveAccount(accountCaptor.capture());

            Account capturedAccount = accountCaptor.getValue();
            assertThat(capturedAccount.isEmailVerified()).isTrue();
        }

        @Test
        @DisplayName("Should activate account with token")
        void shouldActivateAccountWithToken() {
            // Given
            testAccount.verifyEmail();
            when(tokenPort.validateActivationToken("valid-token")).thenReturn(Either.right(accountId));
            when(accountPort.findAccountById(accountId)).thenReturn(Either.right(testAccount));
            when(accountPort.saveAccount(any(Account.class))).thenReturn(Either.right(testAccount));

            // When
            Either<Failure, Account> result = accountService.activateAccount("valid-token");

            // Then
            assertThat(result.isRight()).isTrue();
            assertThat(result.get().isEmailVerified()).isTrue();
        }
    }
}
