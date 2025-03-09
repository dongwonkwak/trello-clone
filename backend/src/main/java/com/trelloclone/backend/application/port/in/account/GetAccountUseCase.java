package com.trelloclone.backend.application.port.in.account;

import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.Account;
import com.trelloclone.backend.domain.model.Id;
import io.vavr.control.Either;


public interface GetAccountUseCase {

    /**
     * ID로 계정을 조회합니다.
     *
     * @param accountId 계정 ID
     * @return 계정 정보 (존재하지 않으면 Failure.NotFoundFailure)
     */
    Either<Failure, Account> getAccountById(Id accountId);

    /**
     * 이메일로 계정을 조회합니다.
     *
     * @param email 이메일
     * @return 계정 정보 (존재하지 않으면 Failure.NotFoundFailure)
     */
    Either<Failure, Account> getAccountByEmail(String email);

    /**
     * 사용자명으로 계정을 조회합니다.
     *
     * @param username 사용자명
     * @return 계정 정보 (존재하지 않으면 Failure.NotFoundFailure)
     */
    Either<Failure, Account> getAccountByUsername(String username);
}
