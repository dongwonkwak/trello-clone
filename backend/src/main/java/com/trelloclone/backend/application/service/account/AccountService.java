package com.trelloclone.backend.application.service.account;

import com.trelloclone.backend.application.port.in.account.CreateAccountUseCase;
import com.trelloclone.backend.application.port.in.account.GetAccountUseCase;
import com.trelloclone.backend.application.port.in.account.UpdateAccountUseCase;
import com.trelloclone.backend.application.port.out.account.AccountPort;
import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.account.Account;
import com.trelloclone.backend.domain.model.account.AccountId;
import com.trelloclone.backend.domain.validation.AccountValidator;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService implements
        CreateAccountUseCase,
        GetAccountUseCase,
        UpdateAccountUseCase {

    private final AccountPort accountPort;
    private final AccountValidator accountValidator;

    @Override
    public Either<Failure, Account> createAccount(CreateAccountCommand command) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Either<Failure, Account> getAccountById(AccountId accountId) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Either<Failure, Account> getAccountByEmail(String email) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Either<Failure, Account> getAccountByUsername(String username) {
        return null;
    }

    @Override
    public Either<Failure, Account> updateAccount(UpdateAccountCommand command) {
        return null;
    }

    @Override
    public Either<Failure, Account> verifyEmail(AccountId accountId) {
        return null;
    }
}
