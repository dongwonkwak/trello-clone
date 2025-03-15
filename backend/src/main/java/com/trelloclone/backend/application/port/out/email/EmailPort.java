package com.trelloclone.backend.application.port.out.email;

import com.trelloclone.backend.common.error.Failure;
import io.vavr.control.Either;

import java.util.Locale;

public interface EmailPort {
    /**
     * 이메일을 전송합니다.
     *
     * @param to 수신자 이메일 주소
     * @param subject 이메일 제목
     * @param content 이메일 내용
     * @return 성공 또는 실패
     */
    Either<Failure, Void> sendEmail(String to, String subject, String content);

    /**
     * 계정 활성화 이메일을 전송합니다.
     *
     * @param to 수신자 이메일 주소
     * @param token 활성화 토큰
     * @param locale 사용자 로케일
     * @return 성공 또는 실패
     */
    Either<Failure, Void> sendAccountActivationEmail(String to, String token, Locale locale);
}