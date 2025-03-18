package com.trelloclone.backend.adapter.out.persistence.mapper;

import com.trelloclone.backend.adapter.out.persistence.entity.AccountEntity;
import com.trelloclone.backend.domain.model.Account;
import com.trelloclone.backend.domain.model.Id;

public class AccountPersistenceMapper {

    private AccountPersistenceMapper() {

    }

    public static Account toDomain(AccountEntity entity) {
        return Account.builder()
                .id(Id.of(entity.getId()))
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .profileImageUrl(entity.getProfileImage())
                .emailVerified(entity.isEmailVerified())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static AccountEntity toEntity(Account account) {
        AccountEntity entity = new AccountEntity();
        entity.setFirstName(account.getFirstName());
        entity.setLastName(account.getLastName());
        entity.setEmail(account.getEmail());
        entity.setPassword(account.getPassword());
        entity.setProfileImage(account.getProfileImageUrl());
        entity.setEmailVerified(account.isEmailVerified());

        return entity;
    }
}
