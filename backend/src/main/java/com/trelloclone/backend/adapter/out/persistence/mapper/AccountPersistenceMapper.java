package com.trelloclone.backend.adapter.out.persistence.mapper;

import com.trelloclone.backend.adapter.out.persistence.entity.AccountEntity;
import com.trelloclone.backend.domain.model.account.Account;
import com.trelloclone.backend.domain.model.account.AccountId;

public class AccountPersistenceMapper {

    private AccountPersistenceMapper() {

    }

    public static Account toDomain(AccountEntity entity) {
        return new Account(
                AccountId.of(entity.getId()),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getFullName(),
                entity.getProfileImage(),
                entity.isEmailVerified(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static AccountEntity toEntity(Account account) {
        AccountEntity entity = new AccountEntity();
        entity.setId(account.getId().id());
        entity.setUsername(account.getUsername());
        entity.setEmail(account.getEmail());
        entity.setPassword(account.getPassword());
        entity.setFullName(account.getFullName());
        entity.setProfileImage(account.getProfileImageUrl());
        entity.setEmailVerified(account.isEmailVerified());
        entity.setCreatedAt(account.getCreatedAt());
        entity.setUpdatedAt(account.getUpdatedAt());
        return entity;
    }
}
