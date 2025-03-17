package com.trelloclone.backend.adapter.in.web.controller;

import com.trelloclone.backend.adapter.in.web.common.ApiFailureHandler;
import com.trelloclone.backend.adapter.in.web.model.AccountResponse;
import com.trelloclone.backend.adapter.in.web.model.Link;
import com.trelloclone.backend.adapter.in.web.model.PutMeRequest;
import com.trelloclone.backend.application.port.in.account.GetAccountUseCase;
import com.trelloclone.backend.application.port.in.account.UpdateAccountUseCase;
import com.trelloclone.backend.application.port.in.account.UpdateAccountUseCase.UpdateAccountCommand;
import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.Account;
import com.trelloclone.backend.domain.model.Id;
import io.vavr.control.Either;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private GetAccountUseCase getAccountUseCase;

    @Mock
    private UpdateAccountUseCase updateAccountUseCase;

    @Mock
    private ApiFailureHandler apiFailureHandler;

    @InjectMocks
    private AccountController accountController;

    private final String testEmail = "test@example.com";
    private final String testFirstname = "john";
    private final String testLastname = "doe";
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        authentication = new UsernamePasswordAuthenticationToken(testEmail, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("사용자 정보 조회 성공 시 200 OK와 계정 정보를 반환한다")
    void getMe_WhenSuccessful_ReturnsOkAndAccountInfo() {
        // Given
        Account account = createTestAccount();
        when(getAccountUseCase.getAccountByEmail(testEmail)).thenReturn(Either.right(account));

        // When
        ResponseEntity<?> response = accountController.getMe();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(AccountResponse.class);
        AccountResponse responseBody = (AccountResponse) response.getBody();
        assert responseBody != null;
        assertThat(responseBody.getEmail()).isEqualTo(testEmail);
        assertThat(responseBody.getFirstname()).isEqualTo(testFirstname);
        assertThat(responseBody.getLastname()).isEqualTo(testLastname);
        verify(getAccountUseCase).getAccountByEmail(testEmail);
    }

    @Test
    @DisplayName("사용자 정보 조회 실패 시 ApiFailureHandler를 호출한다")
    void getMe_WhenFailed_CallsApiFailureHandler() {
        // Given
        Failure failure = mock(Failure.class);
        ResponseEntity<?> expectedResponse = ResponseEntity.badRequest().build();
        when(getAccountUseCase.getAccountByEmail(testEmail)).thenReturn(Either.left(failure));
        when(apiFailureHandler.handle(any(Failure.class))).thenReturn((ResponseEntity)expectedResponse);

        // When
        ResponseEntity<?> response = accountController.getMe();

        // Then
        assertThat(response).isEqualTo(expectedResponse);
        verify(getAccountUseCase).getAccountByEmail(testEmail);
        verify(apiFailureHandler).handle(failure);
    }

    @Test
    @DisplayName("사용자 정보 업데이트 성공 시 200 OK와 업데이트된 계정 정보를 반환한다")
    void putMe_WhenSuccessful_ReturnsOkAndUpdatedAccountInfo() {
        // Given
        Account account = createTestAccount();
        Account updatedAccount = account.toBuilder().firstName("Jane").build();
        PutMeRequest request = new PutMeRequest();
        request.setFirstname("Jane");
        Link profileImage = new Link();
        profileImage.setHref("https://example.com/newimage.jpg");
        request.setProfileImage(profileImage);

        when(getAccountUseCase.getAccountByEmail(testEmail)).thenReturn(Either.right(account));
        when(updateAccountUseCase.updateAccount(any(UpdateAccountUseCase.UpdateAccountCommand.class)))
                .thenReturn(Either.right(updatedAccount));

        // When
        ResponseEntity<?> response = accountController.putMe(request);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(AccountResponse.class);
        verify(getAccountUseCase).getAccountByEmail(testEmail);
        verify(updateAccountUseCase).updateAccount(any(UpdateAccountUseCase.UpdateAccountCommand.class));
    }

    @Test
    @DisplayName("사용자 정보 업데이트 시 모든 필드 제공할 경우 성공적으로 업데이트되어야 한다")
    void putMe_WithAllFieldsProvided_UpdatesSuccessfully() {
        // Given
        Account account = createTestAccount();
        when(getAccountUseCase.getAccountByEmail(testEmail)).thenReturn(Either.right(account));

        PutMeRequest request = new PutMeRequest();
        request.setFirstname("Jane");
        var profileImage = new Link();
        profileImage.setHref("https://example.com/new.jpg");
        request.setProfileImage(profileImage);

        Account updatedAccount = account.toBuilder()
                .firstName("Jane")
                .profileImageUrl("https://example.com/new.jpg")
                .build();

        when(updateAccountUseCase.updateAccount(any(UpdateAccountCommand.class)))
                .thenReturn(Either.right(updatedAccount));

        // When
        ResponseEntity<?> response = accountController.putMe(request);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(AccountResponse.class);
        AccountResponse responseBody = (AccountResponse) response.getBody();
        assert responseBody != null;
        assertThat(responseBody.getFirstname()).isEqualTo("Jane");

        ArgumentCaptor<UpdateAccountCommand> commandCaptor = ArgumentCaptor.forClass(UpdateAccountCommand.class);
        verify(updateAccountUseCase).updateAccount(commandCaptor.capture());
        UpdateAccountCommand capturedCommand = commandCaptor.getValue();
        assertThat(capturedCommand.firstName()).isEqualTo("Jane");
        assertThat(capturedCommand.profileImageUrl()).isEqualTo("https://example.com/new.jpg");
    }

    @Test
    @DisplayName("사용자 정보 업데이트 시 일부 필드만 제공할 경우 기존 값이 유지되어야 한다")
    void putMe_WithPartialFields_KeepsExistingValues() {
        // Given
        Account account = createTestAccount();
        when(getAccountUseCase.getAccountByEmail(testEmail)).thenReturn(Either.right(account));

        PutMeRequest request = new PutMeRequest();
        request.setFirstname("Jane");
        // No profile image provided

        Account updatedAccount = account.toBuilder()
                .firstName("Jane")
                .build();

        when(updateAccountUseCase.updateAccount(any(UpdateAccountCommand.class)))
                .thenReturn(Either.right(updatedAccount));

        // When
        ResponseEntity<?> response = accountController.putMe(request);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        AccountResponse responseBody = (AccountResponse) response.getBody();
        assert responseBody != null;
        assertThat(responseBody.getFirstname()).isEqualTo("Jane");

        ArgumentCaptor<UpdateAccountCommand> commandCaptor = ArgumentCaptor.forClass(UpdateAccountCommand.class);
        verify(updateAccountUseCase).updateAccount(commandCaptor.capture());
        UpdateAccountCommand capturedCommand = commandCaptor.getValue();
        assertThat(capturedCommand.firstName()).isEqualTo("Jane");
        assertThat(capturedCommand.profileImageUrl()).isEqualTo(account.getProfileImageUrl());
    }


    @Test
    @DisplayName("사용자 정보 조회 실패 시 ApiFailureHandler를 통해 에러 응답을 반환한다")
    void putMe_WhenGetAccountFails_ReturnsFailureResponse() {
        // Given
        Failure failure = Failure.ofNotFound("User not found");
        when(getAccountUseCase.getAccountByEmail(testEmail)).thenReturn(Either.left(failure));

        ResponseEntity<?> expectedResponse = ResponseEntity.notFound().build();
        when(apiFailureHandler.handle(failure)).thenReturn((ResponseEntity)expectedResponse);

        PutMeRequest request = new PutMeRequest();

        // When
        ResponseEntity<?> response = accountController.putMe(request);

        // Then
        assertThat(response).isSameAs(expectedResponse);
        verify(apiFailureHandler).handle(failure);
    }

    @Test
    @DisplayName("사용자 정보 업데이트 실패 시 ApiFailureHandler를 통해 에러 응답을 반환한다")
    void putMe_WhenUpdateFails_ReturnsFailureResponse() {
        // Given
        Account account = createTestAccount();
        when(getAccountUseCase.getAccountByEmail(testEmail)).thenReturn(Either.right(account));

        PutMeRequest request = new PutMeRequest();
        request.firstname("Jain");

        Failure failure = Failure.ofValidation("Validation error",
                Collections.singletonList(Failure.FieldViolation.builder()
                        .field("email")
                        .message("Invalid format").build()));
        when(updateAccountUseCase.updateAccount(any(UpdateAccountCommand.class)))
                .thenReturn(Either.left(failure));

        ResponseEntity<?> expectedResponse = ResponseEntity.badRequest().build();
        when(apiFailureHandler.handle(failure)).thenReturn((ResponseEntity)expectedResponse);

        // When
        ResponseEntity<?> response = accountController.putMe(request);

        // Then
        assertThat(response).isSameAs(expectedResponse);
        verify(apiFailureHandler).handle(failure);
    }

    // Helper method for creating test accounts
    private Account createTestAccount() {
        return Account.builder()
                .id(Id.newId())
                .email(testEmail)
                .firstName(testFirstname)
                .lastName(testLastname)
                .profileImageUrl("https://example.com/profile.jpg")
                .emailVerified(true)
                .build();
    }
}
