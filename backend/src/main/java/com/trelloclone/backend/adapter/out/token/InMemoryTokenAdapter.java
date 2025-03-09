package com.trelloclone.backend.adapter.out.token;

import com.trelloclone.backend.application.port.out.token.TokenPort;
import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.Id;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryTokenAdapter implements TokenPort {

    private final ConcurrentHashMap<String, TokenInfo> tokens = new ConcurrentHashMap<>();

    private record TokenInfo(Id accountId, LocalDateTime expiresAt) {
    }

    @Override
    public Either<Failure, String> createActivationToken(Id accountId, LocalDateTime expiresAt) {
        return Try.of(() -> {
                    // 고유한 토큰 생성
                    String tokenValue = generateToken(accountId);

                    // 토큰 저장
                    tokens.put(tokenValue, new TokenInfo(accountId, expiresAt));

                    return tokenValue;
                })
                .toEither()
                .mapLeft(ex -> Failure.ofInternalServerError("Failed to create activation token: " + ex.getMessage()));
    }

    @Override
    public Either<Failure, Id> validateActivationToken(String token) {
        TokenInfo tokenInfo = tokens.get(token);

        if (tokenInfo == null) {
            return Either.left(Failure.ofBadRequest("Invalid activation token"));
        }

        if (tokenInfo.expiresAt.isBefore(LocalDateTime.now())) {
            tokens.remove(token);
            return Either.left(Failure.ofBadRequest("Activation token has expired"));
        }

        // 토큰 사용 후 제거
        tokens.remove(token);

        return Either.right(tokenInfo.accountId);
    }

    private String generateToken(Id accountId) throws Exception {
        String input = accountId.id().toString() + UUID.randomUUID() + System.currentTimeMillis();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
    }
}
