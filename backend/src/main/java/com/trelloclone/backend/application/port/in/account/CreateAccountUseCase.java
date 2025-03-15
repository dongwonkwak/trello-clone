package com.trelloclone.backend.application.port.in.account;

import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.Account;
import io.vavr.control.Either;
import lombok.Builder;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.trim;

public interface CreateAccountUseCase {

    Either<Failure, Account> createAccount(CreateAccountCommand command);

    @Builder
    record CreateAccountCommand(
            String firstName,
            String lastName,
            String email,
            String password,
            String profileImageUrl) {

         public CreateAccountCommand(
                String firstName,
                String lastName,
                String email,
                String password,
                String profileImageUrl) {
            requireNonNull(firstName);
            requireNonNull(lastName);
            requireNonNull(email);
            requireNonNull(password);

            this.firstName = trim(firstName);
            this.lastName = trim(lastName);
            this.email = trim(email);
            this.password = trim(password);
            this.profileImageUrl = trim(profileImageUrl);
        }


        public static final String FIELD_FIRSTNAME = "firstname";
        public static final String FIELD_LASTNAME = "lastname";
        public static final String FIELD_EMAIL = "email";
        public static final String FIELD_PASSWORD = "password";
    }
}
