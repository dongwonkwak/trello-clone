package com.trelloclone.backend.application.service.account;

import com.trelloclone.backend.application.port.in.account.CreateAccountUseCase;
import com.trelloclone.backend.application.port.in.account.GetAccountUseCase;
import com.trelloclone.backend.application.port.in.account.UpdateAccountUseCase;
import com.trelloclone.backend.application.port.out.account.AccountPort;
import com.trelloclone.backend.application.port.out.token.TokenPort;
import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.domain.AccountCreatedEvent;
import com.trelloclone.backend.domain.model.account.Account;
import com.trelloclone.backend.domain.model.account.AccountId;
import com.trelloclone.backend.domain.validation.AccountValidator;
import com.trelloclone.backend.domain.validation.ValidationMessageKeys;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountService implements
        CreateAccountUseCase,
        GetAccountUseCase,
        UpdateAccountUseCase {

    private final AccountPort accountPort;
    private final AccountValidator accountValidator;
    private final PasswordEncoder encoder;
    private final TokenPort tokenPort;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Either<Failure, Account> createAccount(CreateAccountCommand command) {
        var validation = accountValidator.validate(command);
        if (validation.isInvalid()) {
            log.error("Validation failed: {}", validation.getError());
            return Either.left(Failure.ofValidation(
                    ValidationMessageKeys.VALIDATION_ERROR,
                    validation.getError().toJavaList()));
        }

        if (accountPort.existsByEmail(command.email())) {
            log.error("Email already exists: {}", command.email());
            return Either.left(Failure.ofConflict(CreateAccountCommand.FIELD_EMAIL));
        }

        if (accountPort.existsByUsername(command.username())) {
            log.error("Username already exists: {}", command.username());
            return Either.left(Failure.ofConflict(CreateAccountCommand.FIELD_USERNAME));
        }

        var encodedPassword = encoder.encode(command.password());

        var newAccount = Account.create(
                command.username(),
                command.email(),
                encodedPassword,
                command.fullName(),
                command.profileImageUrl());

        return accountPort.saveAccount(newAccount)
                .map(savedAccount -> {
                    tokenPort.createActivationToken(savedAccount.getId(), LocalDateTime.now().plusHours(24))
                            .map(token -> {
                                var locale = LocaleContextHolder.getLocale();
                                var event = AccountCreatedEvent.builder()
                                        .activationToken(token)
                                        .email(command.email())
                                        .username(command.username())
                                        .locale(locale)
                                        .build();
                                eventPublisher.publishEvent(event);
                                return savedAccount;
                            });
                    return savedAccount;
                })
                .mapLeft(failure -> {
                    log.error("Failed to generate token: {}", failure.message());
                    return failure;
                });
    }

    @Override
    public Either<Failure, Account> getAccountById(AccountId accountId) {
        return accountPort.findAccountById(accountId);
    }

    @Override
    @Transactional(readOnly = true)
    public Either<Failure, Account> getAccountByEmail(String email) {
        return accountPort.findAccountByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Either<Failure, Account> getAccountByUsername(String username) {
        return accountPort.findAccountByUsername(username);
    }

    @Override
    public Either<Failure, Account> updateAccount(UpdateAccountCommand command) {
        return getAccountById(command.accountId())
                .map(account -> {
                    account.updateProfile(command.fullName(), command.profileImageUrl());
                    return account;
                })
                .flatMap(accountPort::saveAccount);
    }

    @Override
    public Either<Failure, Account> verifyEmail(AccountId accountId) {
        return getAccountById(accountId)
                .map(account -> {
                    account.verifyEmail();
                    return account;
                })
                .flatMap(accountPort::saveAccount);
    }

    @Override
    public Either<Failure, Account> activateAccount(String token) {
        return tokenPort.validateActivationToken(token)
                .flatMap(this::verifyEmail);
    }
}
