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
                .username(entity.getUsername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .fullName(entity.getFullName())
                .profileImageUrl(entity.getProfileImage())
                .emailVerified(entity.isEmailVerified())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
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
