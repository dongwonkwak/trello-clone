package com.trelloclone.backend.adapter.out.persistence.adapter;

import com.trelloclone.backend.adapter.out.persistence.mapper.AccountPersistenceMapper;
import com.trelloclone.backend.adapter.out.persistence.repository.AccountRepository;
import com.trelloclone.backend.application.port.out.account.AccountPort;
import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.account.Account;
import com.trelloclone.backend.domain.model.account.AccountId;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountPersistenceAdapter implements AccountPort {

    private final AccountRepository accountRepository;

    @Override
    public Either<Failure, Account> saveAccount(Account account) {
        return Try.of(() -> accountRepository.save(AccountPersistenceMapper.toEntity(account)))
                .map(AccountPersistenceMapper::toDomain)
                .map(Either::<Failure, Account>right)
                .getOrElseGet(ex -> Either.left(Failure.ofInternalServerError(ex.getMessage())));
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

    @Override
    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }
}
