package com.trelloclone.backend.application.port.out.account;

import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.account.Account;
import com.trelloclone.backend.domain.model.account.AccountId;
import io.vavr.control.Either;


public interface AccountPort {
    /**
     * 새 계정을 저장합니다.
     *
     * @param account 저장할 계정
     * @return 저장된 계정
     */
    Either<Failure, Account> saveAccount(Account account);

    /**
     * ID로 계정을 조회합니다.
     *
     * @param accountId 계정 ID
     * @return 계정 정보 (존재하지 않으면 empty)
     */
    Either<Failure, Account> findAccountById(AccountId accountId);

    /**
     * 이메일로 계정을 조회합니다.
     *
     * @param email 이메일
     * @return 계정 정보 (존재하지 않으면 empty)
     */
    Either<Failure, Account> findAccountByEmail(String email);

    /**
     * 사용자명으로 계정을 조회합니다.
     *
     * @param username 사용자명
     * @return 계정 정보 (존재하지 않으면 empty)
     */
    Either<Failure, Account> findAccountByUsername(String username);

    /**
     * 계정을 삭제합니다.
     *
     * @param accountId 계정 ID
     */
    void deleteAccount(AccountId accountId);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
