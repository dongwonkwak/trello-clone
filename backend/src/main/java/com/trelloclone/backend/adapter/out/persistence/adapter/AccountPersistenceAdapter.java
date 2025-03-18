package com.trelloclone.backend.adapter.out.persistence.adapter;

import com.trelloclone.backend.adapter.out.persistence.entity.AccountEntity;
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
        // 만약 accountRepository에 AccountEntity가 이미 있다면. 그걸 가져와서 값을 업데이트 하고 저장한다.
        // 없다면 새로운 AccountEntity를 만들어서 저장한다.
        return Try.of(() -> {
            if (account.getId() != null) {
                // Try to find existing account
                return accountRepository.findById(account.getId().id())
                        .map(existingEntity -> {
                            updateEntityFromDomain(existingEntity, account);
                            return accountRepository.save(existingEntity);
                        })
                        .orElseGet(() -> accountRepository.save(AccountPersistenceMapper.toEntity(account)));
            }
            // Create new account if ID does not exist
            return accountRepository.save(AccountPersistenceMapper.toEntity(account));
        })
                .toEither()
                .mapLeft(ex -> Failure.ofInternalServerError(ex.getMessage()))
                .map(AccountPersistenceMapper::toDomain);
    }

    private void updateEntityFromDomain(AccountEntity entity, Account account) {
        entity.setFirstName(account.getFirstName());
        entity.setLastName(account.getLastName());
        entity.setEmail(account.getEmail());
        entity.setPassword(account.getPassword());
        entity.setProfileImage(account.getProfileImageUrl());
        entity.setEmailVerified(account.isEmailVerified());
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
