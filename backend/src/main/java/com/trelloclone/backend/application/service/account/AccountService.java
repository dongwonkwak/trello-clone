package com.trelloclone.backend.application.service.account;

import com.trelloclone.backend.application.port.in.account.CreateAccountUseCase;
import com.trelloclone.backend.application.port.in.account.GetAccountUseCase;
import com.trelloclone.backend.application.port.in.account.ResendActivationUseCase;
import com.trelloclone.backend.application.port.in.account.UpdateAccountUseCase;
import com.trelloclone.backend.application.port.out.account.AccountPort;
import com.trelloclone.backend.application.port.out.token.TokenPort;
import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.event.AccountCreatedEvent;
import com.trelloclone.backend.domain.model.Account;
import com.trelloclone.backend.domain.model.Id;
import com.trelloclone.backend.domain.validation.AccountValidator;
import com.trelloclone.backend.domain.validation.ErrorMessageKeys;
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
import java.util.Collections;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountService implements
        CreateAccountUseCase,
        GetAccountUseCase,
        UpdateAccountUseCase,
        ResendActivationUseCase {

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


        var encodedPassword = encoder.encode(command.password());

        var newAccount = Account.builder()
                .firstName(command.firstName())
                .lastName(command.lastName())
                .email(command.email())
                .password(encodedPassword)
                .profileImageUrl(command.profileImageUrl())
                .build();

        return accountPort.saveAccount(newAccount)
                .map(savedAccount -> {
                    tokenPort.createActivationToken(savedAccount.getId(), LocalDateTime.now().plusHours(24))
                            .map(token -> {
                                var locale = LocaleContextHolder.getLocale();
                                var event = AccountCreatedEvent.builder()
                                        .activationToken(token)
                                        .email(command.email())
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
    public Either<Failure, Account> getAccountById(Id accountId) {
        return accountPort.findAccountById(accountId);
    }

    @Override
    public Either<Failure, Account> getAccountByEmail(String email) {
        return accountPort.findAccountByEmail(email);
    }


    @Override
    public Either<Failure, Account> updateAccount(UpdateAccountCommand command) {
        var validation = accountValidator.validate(command);
        if (validation.isInvalid()) {
            log.error("[updateAccount] Validation failed: {}", validation.getError());
            return Either.left(Failure.ofValidation(
                    ValidationMessageKeys.VALIDATION_ERROR,
                    validation.getError().toJavaList()));
        }

        return getAccountById(command.accountId())
                .map(account ->
                     account.toBuilder()
                            .firstName(command.firstName())
                            .lastName(command.lastName())
                            .profileImageUrl(command.profileImageUrl())
                            .build())
                .flatMap(accountPort::saveAccount);
    }

    @Override
    public Either<Failure, Account> verifyEmail(Id accountId) {
        return getAccountById(accountId)
                .flatMap(account -> {
                    // 이미 활성화 되었다면...
                    if (account.isEmailVerified()) {
                        return Either.left(Failure.ofBadRequest("Email is already verified"));
                    }
                    return Either.right(account.verifyEmail());
                })
                .flatMap(accountPort::saveAccount);
    }

    @Override
    public Either<Failure, Account> activateAccount(String token) {
        if (token == null || token.isBlank()) {
            return Either.left(Failure.ofBadRequest("Token is required"));
        }

        return tokenPort.validateActivationToken(token)
                .flatMap(this::verifyEmail);
    }

    @Override
    @Transactional(readOnly = true)
    public Either<Failure, Void> resendActivation(String email) {
        var validation = accountValidator.validateEmail(email);
        if (validation.isInvalid()) {
            log.error("resendActivation Validation failed: {}", validation.getError());
            return Either.left(Failure.ofValidation(
                    ValidationMessageKeys.VALIDATION_ERROR,
                    Collections.singleton(validation.getError())));
        }

        return getAccountByEmail(email)
                .flatMap(account -> {
                    if (account.isEmailVerified()) {
                        log.error("[resendActivation] Account already verified: {}", email);
                        return Either.left(Failure.ofBadRequest(
                                ErrorMessageKeys.ACCOUNT_ALREADY_VERIFIED));
                    }

                    tokenPort.createActivationToken(account.getId(), LocalDateTime.now().plusHours(24))
                            .map(token -> {
                                var locale = LocaleContextHolder.getLocale();
                                var event = AccountCreatedEvent.builder()
                                        .activationToken(token)
                                        .email(email)
                                        .locale(locale)
                                        .build();
                                eventPublisher.publishEvent(event);
                                return null;
                            });
                    return null;
                });
    }
}
