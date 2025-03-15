package com.trelloclone.backend.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode(of = "id")
@Builder(builderClassName = "AccountBuilder", buildMethodName = "_build")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {

    private final Id id;
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String profileImageUrl;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder.Default
    private boolean emailVerified = false;

    // 사용자가 소유한 보드
    @Singular
    private final List<Board> ownedBoards;

    // 사용자가 참여한 보드
    @Singular
    private final List<BoardMember> boardMemberships;

    public static class AccountBuilder extends BaseEntityBuilder<Account, AccountBuilder> {

        @Override
        public Account build() {
            setupDefaults();
            return _build();
        }
    }

    public AccountBuilder toBuilder() {
        return new AccountBuilder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .profileImageUrl(this.profileImageUrl)
                .emailVerified(this.emailVerified)
                .ownedBoards(new ArrayList<>(this.ownedBoards))
                .boardMemberships(new ArrayList<>(this.boardMemberships))
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now());
    }

    public static AccountBuilder builder() {
        return new AccountBuilder();
    }

    public Account verifyEmail() {
        return this.toBuilder()
                .emailVerified(true)
                .build();
    }
}
