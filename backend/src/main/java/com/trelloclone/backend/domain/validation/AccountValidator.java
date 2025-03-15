package com.trelloclone.backend.domain.validation;

import com.trelloclone.backend.application.port.in.account.CreateAccountUseCase.CreateAccountCommand;
import com.trelloclone.backend.application.port.in.account.UpdateAccountUseCase.UpdateAccountCommand;
import com.trelloclone.backend.common.error.Failure.FieldViolation;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static io.vavr.API.*;

@Component
public final class AccountValidator {
    public static final String NAME_PATTERN = "^[\\p{L}]+$";
    public static final String EMAIL_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";

    public Validation<Seq<FieldViolation>, CreateAccountCommand> validate(CreateAccountCommand command) {
        return Validation.combine(
                validateFirstName(command.firstName()),
                validateLastName(command.lastName()),
                validateEmail(command.email()),
                validatePassword(command.password())
        ).ap((firstName, lastName,email, password) -> command);
    }

    public Validation<Seq<FieldViolation>, UpdateAccountCommand> validate(UpdateAccountCommand command) {
        return Validation.combine(
                validateFirstName(command.firstName()),
                validateLastName(command.lastName())
        ).ap((firstName, lastName) -> command);
    }

    public Validation<FieldViolation, String> validateFirstName(String firstName) {
        if (StringUtils.isBlank(firstName)) {
            return Invalid(
                    FieldViolation.builder()
                            .field(CreateAccountCommand.FIELD_FIRSTNAME)
                            .message(ValidationMessageKeys.FIRSTNAME_EMPTY)
                            .build());
        }

        if (firstName.length() < 2 || firstName.length() > 50) {
            return Invalid(
                    FieldViolation.builder()
                            .field(CreateAccountCommand.FIELD_FIRSTNAME)
                            .message(ValidationMessageKeys.FIRSTNAME_SIZE)
                            .args(new Object[]{2, 50})
                            .rejectedValue(firstName)
                            .build());
        }

        if (!Pattern.compile(NAME_PATTERN).matcher(firstName).matches()) {
            return Invalid(
                    FieldViolation.builder()
                            .field(CreateAccountCommand.FIELD_FIRSTNAME)
                            .message(ValidationMessageKeys.FIRSTNAME_PATTERN)
                            .rejectedValue(firstName)
                            .build());
        }

        return Valid(firstName);
    }

    public Validation<FieldViolation, String> validateLastName(String lastName) {
        if (StringUtils.isBlank(lastName)) {
            return Invalid(
                    FieldViolation.builder()
                            .field(CreateAccountCommand.FIELD_LASTNAME)
                            .message(ValidationMessageKeys.LASTNAME_EMPTY)
                            .build());
        }

        if (lastName.length() < 2 || lastName.length() > 50) {
            return Invalid(
                    FieldViolation.builder()
                            .field(CreateAccountCommand.FIELD_LASTNAME)
                            .message(ValidationMessageKeys.LASTNAME_SIZE)
                            .args(new Object[]{2, 50})
                            .rejectedValue(lastName)
                            .build());
        }

        if (!Pattern.compile(NAME_PATTERN).matcher(lastName).matches()) {
            return Invalid(
                    FieldViolation.builder()
                            .field(CreateAccountCommand.FIELD_LASTNAME)
                            .message(ValidationMessageKeys.LASTNAME_PATTERN)
                            .rejectedValue(lastName)
                            .build());
        }

        return Valid(lastName);
    }


    public Validation<FieldViolation, String> validateEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return Invalid(
                    FieldViolation.builder()
                            .field(CreateAccountCommand.FIELD_EMAIL)
                            .message(ValidationMessageKeys.EMAIL_EMPTY)
                            .build());
        }

        if (email.length() > 255) {
            return Invalid(
                    FieldViolation.builder()
                            .field(CreateAccountCommand.FIELD_EMAIL)
                            .message(ValidationMessageKeys.EMAIL_MAX_SIZE)
                            .args(new Object[]{255})
                            .rejectedValue(email)
                            .build());
        }

        if (!Pattern.compile(EMAIL_PATTERN).matcher(email).matches()) {
            return Invalid(
                    FieldViolation.builder()
                            .field(CreateAccountCommand.FIELD_EMAIL)
                            .message(ValidationMessageKeys.EMAIL_INVALID)
                            .rejectedValue(email)
                            .build());
        }

        return Valid(email);
    }

    public Validation<FieldViolation, String> validatePassword(String password) {
        if (StringUtils.isBlank(password)) {
            return Invalid(
                    FieldViolation.builder()
                            .field(CreateAccountCommand.FIELD_PASSWORD)
                            .message(ValidationMessageKeys.PASSWORD_EMPTY)
                            .build());
        }

        if (password.length() < 8 || password.length() > 20) {
            return Invalid(
                    FieldViolation.builder()
                            .field(CreateAccountCommand.FIELD_PASSWORD)
                            .message(ValidationMessageKeys.PASSWORD_SIZE)
                            .args(new Object[]{8, 20})
                            .rejectedValue(password)
                            .build());
        }

        // !@#$%^&*()_+
        String specialChars = "!@#$%^&*()_+";
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (specialChars.indexOf(c) != -1) {
                hasSpecial = true;
            }

            if (hasUppercase && hasLowercase && hasDigit && hasSpecial) {
                break;
            }
        }

        if (!hasUppercase) {
            return Invalid(
                    FieldViolation.builder()
                            .field(CreateAccountCommand.FIELD_PASSWORD)
                            .message(ValidationMessageKeys.PASSWORD_UPPERCASE)
                            .rejectedValue(password)
                            .build());
        }

        if (!hasLowercase) {
            return Invalid(
                    FieldViolation.builder()
                            .field(CreateAccountCommand.FIELD_PASSWORD)
                            .message(ValidationMessageKeys.PASSWORD_LOWERCASE)
                            .rejectedValue(password)
                            .build());
        }

        if (!hasDigit) {
            return Invalid(
                    FieldViolation.builder()
                            .field(CreateAccountCommand.FIELD_PASSWORD)
                            .message(ValidationMessageKeys.PASSWORD_DIGIT)
                            .rejectedValue(password)
                            .build());
        }

        if (!hasSpecial) {
            return Invalid(
                    FieldViolation.builder()
                            .field(CreateAccountCommand.FIELD_PASSWORD)
                            .message(ValidationMessageKeys.PASSWORD_SPECIAL)
                            .rejectedValue(password)
                            .build());
        }

        if (!Pattern.compile(PASSWORD_PATTERN).matcher(password).matches()) {
            return Invalid(
                    FieldViolation.builder()
                            .field(CreateAccountCommand.FIELD_PASSWORD)
                            .message(ValidationMessageKeys.PASSWORD_PATTERN)
                            .rejectedValue(password)
                            .build());
        }
        return Valid(password);
    }
}
