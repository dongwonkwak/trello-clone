package com.trelloclone.backend.adapter.in.web.mapper;

import com.trelloclone.backend.adapter.in.web.model.AccountResponse;
import com.trelloclone.backend.domain.model.Account;
import com.trelloclone.backend.domain.model.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class AccountMapperTest {

    @Test
    @DisplayName("Account 도메인 객체를 AccountResponse로 정확하게 변환해야 한다")
    void toResponse_ShouldMapAccountToAccountResponse() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Id id = Id.newId();
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String profileImageUrl = "http://example.com/profile.jpg";

        Account account = Account.builder()
                .id(id)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .profileImageUrl(profileImageUrl)
                .createdAt(now)
                .updatedAt(now)
                .build();

        // when
        AccountResponse response = AccountMapper.toResponse(account);

        // then
        assertThat(response.getId()).isEqualTo(id.id());
        assertThat(response.getEmail()).isEqualTo(email);
        assertThat(response.getFirstname()).isEqualTo(firstName);
        assertThat(response.getLastname()).isEqualTo(lastName);
        assert response.getProfileImage() != null;
        assertThat(response.getProfileImage().getHref()).isEqualTo(profileImageUrl);
        assertThat(response.getCreatedAt()).isEqualTo(now);
        assertThat(response.getUpdatedAt()).isEqualTo(now);
    }
}
