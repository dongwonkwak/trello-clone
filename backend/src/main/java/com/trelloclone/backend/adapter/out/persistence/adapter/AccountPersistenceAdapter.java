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
                .toEither()
                .mapLeft(ex -> Failure.ofInternalServerError(ex.getMessage()))
                .map(AccountPersistenceMapper::toDomain);
    }

    @Override
    public Either<Failure, Account> findAccountById(AccountId accountId) {
        return Try.of(() -> accountRepository.findById((accountId.id())))
                .toEither()
                .mapLeft(ex -> Failure.ofInternalServerError(ex.getMessage()))
                .flatMap(optionalAccount -> optionalAccount
                        .map(AccountPersistenceMapper::toDomain)
                        .map(Either::<Failure, Account>right)
                        .orElseGet(() -> Either.left(Failure.ofNotFound("Account not found with id: " + accountId.id().toString()))));
    }

    @Override
    public Either<Failure, Account> findAccountByEmail(String email) {
        return Try.of(() -> accountRepository.findByEmail(email))
                .toEither()
                .mapLeft(ex -> Failure.ofInternalServerError(ex.getMessage()))
                .flatMap(optionalAccount -> optionalAccount
                        .map(AccountPersistenceMapper::toDomain)
                        .map(Either::<Failure, Account>right)
                        .orElseGet(() -> Either.left(Failure.ofNotFound("Account not found with email: " + email))));
    }

    @Override
    public Either<Failure, Account> findAccountByUsername(String username) {
        return Try.of(() -> accountRepository.findByUsername(username))
                .toEither()
                .mapLeft(ex -> Failure.ofInternalServerError(ex.getMessage()))
                .flatMap(optionalAccount -> optionalAccount
                        .map(AccountPersistenceMapper::toDomain)
                        .map(Either::<Failure, Account>right)
                        .orElseGet(() -> Either.left(Failure.ofNotFound("Account not found with username: " + username))));
    }

    @Override
    public void deleteAccount(AccountId accountId) {
        accountRepository.deleteById(accountId.id());
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
