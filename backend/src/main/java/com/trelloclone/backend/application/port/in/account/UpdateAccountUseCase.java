package com.trelloclone.backend.application.port.in.account;

import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.account.Account;
import com.trelloclone.backend.domain.model.account.AccountId;
import io.vavr.control.Either;

public interface UpdateAccountUseCase {

    Either<Failure, Account> updateAccount(UpdateAccountCommand command);

    record UpdateAccountCommand(
            AccountId accountId,
            String fullName,
            String profileImageUrl) {

    }

    /**
     * 계정의 이메일 인증 상태를 변경합니다.
     *
     * @param accountId 계정 ID
     * @return 업데이트된 계정
     */
    Either<Failure, Account> verifyEmail(AccountId accountId);
}
