package com.trelloclone.backend.adapter.in.web.controller;

import com.trelloclone.backend.adapter.in.web.common.ApiFailureHandler;
import com.trelloclone.backend.adapter.in.web.model.SignupRequest;
import com.trelloclone.backend.application.port.in.account.CreateAccountUseCase;
import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.Account;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationControllerTest {

    @Mock
    private CreateAccountUseCase createAccountUseCase;

    @Mock
    private ApiFailureHandler apiFailureHandler;

    @InjectMocks
    private AuthorizationController controller;

    private SignupRequest signupRequest;

    @BeforeEach
    void setUp() {
        signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setUsername("testuser");
        signupRequest.setPassword("Password1!");
    }

    @Test
    @DisplayName("회원가입 성공 시 201 Created 상태코드를 반환한다")
    void signup_WhenSuccessful_ReturnsCreatedStatus() {
        // Given
        Account mockAccount = mock(Account.class);
        when(createAccountUseCase.createAccount(any(CreateAccountUseCase.CreateAccountCommand.class)))
                .thenReturn(Either.right(mockAccount));

        // When
        ResponseEntity<?> response = controller.signup(signupRequest);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(createAccountUseCase).createAccount(any(CreateAccountUseCase.CreateAccountCommand.class));
    }

    @Test
    @DisplayName("회원가입 실패 시 ApiFailureHandler를 호출한다")
    void signup_WhenFailed_CallsApiFailureHandler() {
        // Given
        Failure mockFailure = mock(Failure.class);
        ResponseEntity<?> expectedResponse = ResponseEntity.badRequest().build();

        when(createAccountUseCase.createAccount(any(CreateAccountUseCase.CreateAccountCommand.class)))
                .thenReturn(Either.left(mockFailure));
        when(apiFailureHandler.handle(any(Failure.class))).thenReturn((ResponseEntity)expectedResponse);

        // When
        ResponseEntity<?> response = controller.signup(signupRequest);

        // Then
        assertThat(response).isEqualTo(expectedResponse);
        verify(createAccountUseCase).createAccount(any(CreateAccountUseCase.CreateAccountCommand.class));
        verify(apiFailureHandler).handle(mockFailure);
    }

    @Test
    @DisplayName("회원가입 실패 - 이미 가입된 이메일인 경우, CONFLICT 에러를 반환한다")
    void signup_WhenEmailConflict_ReturnsConflictError() {
        // Given
        Failure conflictFailure = Failure.ofConflict("email");
        ResponseEntity<?> expectedResponse = ResponseEntity.status(HttpStatus.CONFLICT).build();

        when(createAccountUseCase.createAccount(any(CreateAccountUseCase.CreateAccountCommand.class)))
                .thenReturn(Either.left(conflictFailure));
        when(apiFailureHandler.handle(any(Failure.class))).thenReturn((ResponseEntity)expectedResponse);

        // When
        ResponseEntity<?> response = controller.signup(signupRequest);

        // Then
        assertThat(response).isEqualTo(expectedResponse);
        verify(createAccountUseCase).createAccount(any(CreateAccountUseCase.CreateAccountCommand.class));
        verify(apiFailureHandler).handle(conflictFailure);
    }

    @Test
    @DisplayName("회원가입 실패 - 이미 존재하는 username인 경우, CONFLICT 에러를 반환한다")
    void signup_WhenUsernameConflict_ReturnsConflictError() {
        // Given
        Failure conflictFailure = Failure.ofConflict("username");
        ResponseEntity<?> expectedResponse = ResponseEntity.status(HttpStatus.CONFLICT).build();

        when(createAccountUseCase.createAccount(any(CreateAccountUseCase.CreateAccountCommand.class)))
                .thenReturn(Either.left(conflictFailure));
        when(apiFailureHandler.handle(any(Failure.class))).thenReturn((ResponseEntity)expectedResponse);

        // When
        ResponseEntity<?> response = controller.signup(signupRequest);

        // Then
        assertThat(response).isEqualTo(expectedResponse);
        verify(createAccountUseCase).createAccount(any(CreateAccountUseCase.CreateAccountCommand.class));
        verify(apiFailureHandler).handle(conflictFailure);
    }
}
