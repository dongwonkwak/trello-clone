package com.trelloclone.backend.application.port.in.account;

import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.Account;
import com.trelloclone.backend.domain.model.Id;
import io.vavr.control.Either;
import lombok.Builder;

public interface UpdateAccountUseCase {

    Either<Failure, Account> updateAccount(UpdateAccountCommand command);

    @Builder
    record UpdateAccountCommand(
            Id accountId,
            String fullName,
            String profileImageUrl) {

    }

    /**
     * 계정의 이메일 인증 상태를 변경합니다.
     *
     * @param accountId 계정 ID
     * @return 업데이트된 계정
     */
    Either<Failure, Account> verifyEmail(Id accountId);

    /**
     * 계정 활성화 토큰을 검증하고 계정을 활성화합니다.
     *
     * @param token 활성화 토큰
     * @return 활성화된 계정
     */
    Either<Failure, Account> activateAccount(String token);

}
