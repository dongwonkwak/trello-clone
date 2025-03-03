package com.trelloclone.backend.domain.validation;

import com.trelloclone.backend.application.port.in.account.CreateAccountUseCase.CreateAccountCommand;
import com.trelloclone.backend.common.error.Failure;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class AccountValidatorTest {

    private AccountValidator validator;

    @BeforeEach
    void setUp() {
        validator = new AccountValidator();
    }

    @Nested
    @DisplayName("Username validation")
    class UsernameValidationTests {
        @Test
        @DisplayName("Should validate correct username")
        void shouldValidateCorrectUsername() {
            Validation<Failure.FieldViolation, String> result = validator.validateUsername("validUser123");

            assertThat(result.isValid()).isTrue();
            assertThat(result.get()).isEqualTo("validUser123");
        }

        @Test
        @DisplayName("Should reject empty username")
        void shouldRejectEmptyUsername() {
            Validation<Failure.FieldViolation, String> result = validator.validateUsername("");

            assertThat(result.isInvalid()).isTrue();
            assertThat(result.getError().message()).isEqualTo(ValidationMessageKeys.USERNAME_EMPTY);
        }

        @ParameterizedTest
        @ValueSource(strings = {"ab", "abcdefghijklmnopqrstu"})
        @DisplayName("Should reject username with invalid length")
        void shouldRejectInvalidLengthUsername(String username) {
            Validation<Failure.FieldViolation, String> result = validator.validateUsername(username);

            assertThat(result.isInvalid()).isTrue();
            assertThat(result.getError().message()).isEqualTo(ValidationMessageKeys.USERNAME_SIZE);
        }

        @ParameterizedTest
        @ValueSource(strings = {"user@name", "user-name", "user.name"})
        @DisplayName("Should reject username with invalid characters")
        void shouldRejectInvalidCharactersUsername(String username) {
            Validation<Failure.FieldViolation, String> result = validator.validateUsername(username);

            assertThat(result.isInvalid()).isTrue();
            assertThat(result.getError().message()).isEqualTo(ValidationMessageKeys.USERNAME_PATTERN);
        }
    }

    @Nested
    @DisplayName("Email validation")
    class EmailValidationTests {
        @Test
        @DisplayName("Should validate correct email")
        void shouldValidateCorrectEmail() {
            Validation<Failure.FieldViolation, String> result = validator.validateEmail("user@example.com");

            assertThat(result.isValid()).isTrue();
            assertThat(result.get()).isEqualTo("user@example.com");
        }

        @Test
        @DisplayName("Should reject empty email")
        void shouldRejectEmptyEmail() {
            Validation<Failure.FieldViolation, String> result = validator.validateEmail("");

            assertThat(result.isInvalid()).isTrue();
            assertThat(result.getError().message()).isEqualTo(ValidationMessageKeys.EMAIL_EMPTY);
        }

        @Test
        @DisplayName("Should reject too long email")
        void shouldRejectTooLongEmail() {
            String longEmail = "a".repeat(250) + "@example.com";

            Validation<Failure.FieldViolation, String> result = validator.validateEmail(longEmail);

            assertThat(result.isInvalid()).isTrue();
            assertThat(result.getError().message()).isEqualTo(ValidationMessageKeys.EMAIL_MAX_SIZE);
        }

        @ParameterizedTest
        @ValueSource(strings = {"user@", "@example.com", "user@example", "user.example.com"})
        @DisplayName("Should reject invalid email format")
        void shouldRejectInvalidEmailFormat(String email) {
            Validation<Failure.FieldViolation, String> result = validator.validateEmail(email);

            assertThat(result.isInvalid()).isTrue();
            assertThat(result.getError().message()).isEqualTo(ValidationMessageKeys.EMAIL_INVALID);
        }
    }

    @Nested
    @DisplayName("Password validation")
    class PasswordValidationTests {
        @Test
        @DisplayName("Should validate correct password")
        void shouldValidateCorrectPassword() {
            Validation<Failure.FieldViolation, String> result = validator.validatePassword("Password123");

            assertThat(result.isValid()).isTrue();
            assertThat(result.get()).isEqualTo("Password123");
        }

        @Test
        @DisplayName("Should reject empty password")
        void shouldRejectEmptyPassword() {
            Validation<Failure.FieldViolation, String> result = validator.validatePassword("");

            assertThat(result.isInvalid()).isTrue();
            assertThat(result.getError().message()).isEqualTo(ValidationMessageKeys.PASSWORD_EMPTY);
        }

        @ParameterizedTest
        @ValueSource(strings = {"Pass12", "PasswordPasswordPassword12345"})
        @DisplayName("Should reject password with invalid length")
        void shouldRejectInvalidLengthPassword(String password) {
            Validation<Failure.FieldViolation, String> result = validator.validatePassword(password);

            assertThat(result.isInvalid()).isTrue();
            assertThat(result.getError().message()).isEqualTo(ValidationMessageKeys.PASSWORD_SIZE);
        }

        @Test
        @DisplayName("Should reject password without uppercase")
        void shouldRejectPasswordWithoutUppercase() {
            Validation<Failure.FieldViolation, String> result = validator.validatePassword("password123");

            assertThat(result.isInvalid()).isTrue();
            assertThat(result.getError().message()).isEqualTo(ValidationMessageKeys.PASSWORD_UPPERCASE);
        }

        @Test
        @DisplayName("Should reject password without lowercase")
        void shouldRejectPasswordWithoutLowercase() {
            Validation<Failure.FieldViolation, String> result = validator.validatePassword("PASSWORD123");

            assertThat(result.isInvalid()).isTrue();
            assertThat(result.getError().message()).isEqualTo(ValidationMessageKeys.PASSWORD_LOWERCASE);
        }

        @Test
        @DisplayName("Should reject password without digit")
        void shouldRejectPasswordWithoutDigit() {
            Validation<Failure.FieldViolation, String> result = validator.validatePassword("PasswordTest");

            assertThat(result.isInvalid()).isTrue();
            assertThat(result.getError().message()).isEqualTo(ValidationMessageKeys.PASSWORD_DIGIT);
        }
    }

    @Nested
    @DisplayName("Combined validation")
    class CombinedValidationTests {
        @Test
        @DisplayName("Should validate correct command")
        void shouldValidateCorrectCommand() {
            var command = CreateAccountCommand.builder()
                    .username("validUser")
                    .email("user@example.com")
                    .password("Password123!")
                    .build();

            Validation<Seq<Failure.FieldViolation>, CreateAccountCommand> result = validator.validate(command);

            assertThat(result.isValid()).isTrue();
            assertThat(result.get()).isEqualTo(command);
        }

        @Test
        @DisplayName("Should collect all validation errors")
        void shouldCollectAllValidationErrors() {
            var command = CreateAccountCommand.builder()
                    .username("")
                    .email("invalid-email")
                    .password("password")
                    .build();

            Validation<Seq<Failure.FieldViolation>, CreateAccountCommand> result = validator.validate(command);

            assertThat(result.isInvalid()).isTrue();
            assertThat(result.getError().size()).isEqualTo(3);

            assertThat(result.getError()).anySatisfy(v ->
                    assertThat(v.message()).isEqualTo(ValidationMessageKeys.USERNAME_EMPTY));
            assertThat(result.getError()).anySatisfy(v ->
                    assertThat(v.message()).isEqualTo(ValidationMessageKeys.EMAIL_INVALID));
            assertThat(result.getError()).anySatisfy(v ->
                    assertThat(v.message()).isEqualTo(ValidationMessageKeys.PASSWORD_UPPERCASE));
        }
    }
}
