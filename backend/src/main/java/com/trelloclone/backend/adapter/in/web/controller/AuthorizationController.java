package com.trelloclone.backend.adapter.in.web.controller;

import com.trelloclone.backend.adapter.in.web.api.AuthApi;
import com.trelloclone.backend.adapter.in.web.common.ApiFailureHandler;
import com.trelloclone.backend.adapter.in.web.model.SignupRequest;
import com.trelloclone.backend.application.port.in.account.CreateAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import static org.springframework.http.HttpStatus.CREATED;

@Controller
@RequiredArgsConstructor
public class AuthorizationController implements AuthApi {

    private final CreateAccountUseCase createAccountUseCase;
    private final ApiFailureHandler apiFailureHandler;

    @Override
    @PostMapping("/v1/signup")
    public ResponseEntity<?> signup(SignupRequest signupRequest) {
        var command = CreateAccountUseCase.CreateAccountCommand.builder()
                        .email(signupRequest.getEmail())
                        .firstName(signupRequest.getFirstName())
                        .lastName(signupRequest.getLastName())
                        .password(signupRequest.getPassword())
                        .build();

        return createAccountUseCase.createAccount(command)
                .fold(apiFailureHandler::handle,
                account -> ResponseEntity.status(CREATED).build());
    }
}
