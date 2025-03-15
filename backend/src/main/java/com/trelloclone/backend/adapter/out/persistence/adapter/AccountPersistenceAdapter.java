package com.trelloclone.backend.adapter.out.persistence.adapter;

import com.trelloclone.backend.adapter.out.persistence.mapper.AccountPersistenceMapper;
import com.trelloclone.backend.adapter.out.persistence.repository.AccountRepository;
import com.trelloclone.backend.application.port.out.account.AccountPort;
import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.Account;
import com.trelloclone.backend.domain.model.Id;
import com.trelloclone.backend.domain.validation.ErrorMessageKeys;
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
    public Either<Failure, Account> findAccountById(Id accountId) {
        return Try.of(() -> accountRepository.findById((accountId.id())))
                .toEither()
                .mapLeft(ex -> Failure.ofInternalServerError(ex.getMessage()))
                .flatMap(optionalAccount -> optionalAccount
                        .map(AccountPersistenceMapper::toDomain)
                        .map(Either::<Failure, Account>right)
                        .orElseGet(() -> Either.left(Failure.ofNotFound(ErrorMessageKeys.ACCOUNT_NOT_FOUND))));
    }

    @Override
    public Either<Failure, Account> findAccountByEmail(String email) {
        return Try.of(() -> accountRepository.findByEmail(email))
                .toEither()
                .mapLeft(ex -> Failure.ofInternalServerError(ex.getMessage()))
                .flatMap(optionalAccount -> optionalAccount
                        .map(AccountPersistenceMapper::toDomain)
                        .map(Either::<Failure, Account>right)
                        .orElseGet(() -> Either.left(Failure.ofNotFound(ErrorMessageKeys.ACCOUNT_NOT_FOUND))));
    }


    @Override
    public void deleteAccount(Id accountId) {
        accountRepository.deleteById(accountId.id());
    }

    @Override
    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

}
