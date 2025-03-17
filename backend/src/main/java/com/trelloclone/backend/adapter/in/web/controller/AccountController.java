package com.trelloclone.backend.adapter.in.web.controller;

import com.trelloclone.backend.adapter.in.web.api.AccountApi;
import com.trelloclone.backend.adapter.in.web.common.ApiFailureHandler;
import com.trelloclone.backend.adapter.in.web.mapper.AccountMapper;
import com.trelloclone.backend.adapter.in.web.model.PutMeRequest;
import com.trelloclone.backend.application.port.in.account.GetAccountUseCase;
import com.trelloclone.backend.application.port.in.account.UpdateAccountUseCase;
import com.trelloclone.backend.application.port.in.account.UpdateAccountUseCase.UpdateAccountCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AccountController implements AccountApi {

    private final GetAccountUseCase getAccountUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;
    private final ApiFailureHandler apiFailureHandler;

    @Override
    public ResponseEntity<?> getMe() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var email = authentication.getName();
        log.info("getMe: email={}", email);

        return getAccountUseCase.getAccountByEmail(email)
                .fold(apiFailureHandler::handle,
                        account -> ResponseEntity.ok(AccountMapper.toResponse(account)));
    }

    @Override
    public ResponseEntity<?> putMe(PutMeRequest putMeRequest) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var email = authentication.getName();
        log.info("putMe: email={}", email);

        return getAccountUseCase.getAccountByEmail(email)
                .fold(apiFailureHandler::handle,
                        account -> {
                            var url = putMeRequest.getProfileImage() != null
                                    ? putMeRequest.getProfileImage().getHref()
                                    : account.getProfileImageUrl();

                            var command = UpdateAccountCommand.builder()
                                    .accountId(account.getId())
                                    .firstName(putMeRequest.getFirstname())
                                    .lastName(putMeRequest.getLastname())
                                    .profileImageUrl(url)
                                    .build();
                            return updateAccountUseCase.updateAccount(command)
                                    .fold(apiFailureHandler::handle,
                                            updatedAccount ->
                                                    ResponseEntity.ok(AccountMapper.toResponse(updatedAccount)));
                        });
    }
}
