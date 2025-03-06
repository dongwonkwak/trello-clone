package com.trelloclone.backend.application.port.in.account;

import com.trelloclone.backend.common.error.Failure;
import io.vavr.control.Either;

public interface ResendActivationUseCase {

    /**
     * 계정 활성화 링크를 재전송합니다.
     *
     * @param email 사용자 이메일
     * @return 성공 여부
     */
    Either<Failure, Void> resendActivation(String email);

}
