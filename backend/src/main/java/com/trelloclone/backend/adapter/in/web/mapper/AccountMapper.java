package com.trelloclone.backend.adapter.in.web.mapper;

import com.trelloclone.backend.adapter.in.web.model.AccountResponse;
import com.trelloclone.backend.adapter.in.web.model.Link;
import com.trelloclone.backend.domain.model.Account;

public class AccountMapper {

    private AccountMapper() {

    }

    public static AccountResponse toResponse(Account account) {
        return new AccountResponse()
                .id(account.getId().id())
                .email(account.getEmail())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .profileImage(new Link(account.getProfileImageUrl()))
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt());
    }
}
