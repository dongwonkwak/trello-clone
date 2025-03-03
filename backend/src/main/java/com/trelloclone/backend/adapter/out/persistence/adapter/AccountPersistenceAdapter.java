package com.trelloclone.backend.adapter.out.persistence.adapter;

import com.trelloclone.backend.application.port.out.account.AccountPort;
import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.account.Account;
import com.trelloclone.backend.domain.model.account.AccountId;
import io.vavr.control.Either;
import org.springframework.stereotype.Component;

@Component
public class AccountPersistenceAdapter implements AccountPort {
    @Override
    public Either<Failure, Account> saveAccount(Account account) {
        return null;
    }

    @Override
    public Either<Failure, Account> findAccountById(AccountId accountId) {
        return null;
    }

    @Override
    public Either<Failure, Account> findAccountByEmail(String email) {
        return null;
    }

    @Override
    public Either<Failure, Account> findAccountByUsername(String username) {
        return null;
    }

    @Override
    public void deleteAccount(AccountId accountId) {

    }
}
