package com.trelloclone.backend.adapter.in.web.controller;

import com.trelloclone.backend.application.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AccountActivationController {

    private final AccountService accountService;

    @Value("${application.frontend-url}")
    private String frontendUrl;

    @GetMapping("/activate")
    public String activateAccount(@RequestParam("token") String token, Model model) {
        return accountService.activateAccount(token)
                .fold(
                        failure -> {
                            // 활성화 실패
                            model.addAttribute("frontendUrl", frontendUrl);
                            model.addAttribute("success", false);
                            model.addAttribute("message", failure.message());
                            return "account-activation-result";
                        },
                        account -> {
                            // 활성화 성공
                            model.addAttribute("success", true);
                            model.addAttribute("username", account.getUsername());
                            return "account-activation-result";
                        }
                );
    }
}
