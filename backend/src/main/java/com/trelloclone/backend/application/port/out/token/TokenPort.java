package com.trelloclone.backend.application.port.out.token;

import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.account.AccountId;
import io.vavr.control.Either;

import java.time.LocalDateTime;
import java.util.Date;

public interface TokenPort {
    /**
     * 계정 활성화 토큰을 생성합니다.
     *
     * @param accountId 계정 ID
     * @return 생성된 토큰
     */
    Either<Failure, String> createActivationToken(AccountId accountId, LocalDateTime expiresAt);

    /**
     * 계정 활성화 토큰을 검증합니다.
     *
     * @param token 활성화 토큰
     * @return 토큰과 연결된 계정 ID
     */
    Either<Failure, AccountId> validateActivationToken(String token);
}