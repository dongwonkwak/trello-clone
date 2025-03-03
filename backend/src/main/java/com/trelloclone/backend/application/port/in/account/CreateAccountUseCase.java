package com.trelloclone.backend.application.port.in.account;

import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.account.Account;
import io.vavr.control.Either;
import lombok.Builder;

import static java.util.Objects.requireNonNull;

public interface CreateAccountUseCase {

    Either<Failure, Account> createAccount(CreateAccountCommand command);

    @Builder
    record CreateAccountCommand(
            String username,
            String email,
            String password,
            String fullName,
            String profileImageUrl) {

        public CreateAccountCommand {
            requireNonNull(username);
            requireNonNull(email);
            requireNonNull(password);
        }

        public static final String FIELD_USERNAME = "username";
        public static final String FIELD_EMAIL = "email";
        public static final String FIELD_PASSWORD = "password";
    }
}
