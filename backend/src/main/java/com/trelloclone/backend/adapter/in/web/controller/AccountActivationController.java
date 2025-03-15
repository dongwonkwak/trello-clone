package com.trelloclone.backend.adapter.in.web.controller;

import com.trelloclone.backend.application.service.account.AccountService;
import com.trelloclone.backend.infrastructure.MessageGetter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AccountActivationController {

    private final AccountService accountService;
    private final MessageGetter messageGetter;

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
                            model.addAttribute("email", account.getEmail());
                            return "account-activation-result";
                        }
                );
    }

    @GetMapping("/resend-activation")
    public String showResendActivationForm(Model model) {
        model.addAttribute("frontendUrl", frontendUrl);
        return "resend-activation-form";
    }

    @PostMapping("/resend-activation")
    public String resendActivation(
            @RequestParam("email") String email,
            Model model,
            RedirectAttributes redirectAttributes) {

        // 활성화 이메일 재전송 성공시 화면
        return accountService.resendActivation(email)
                .fold(
                        failure -> {
                            // 활성화 이메일 재전송 실패
                            var errorMessage = messageGetter.getMessage(failure.message());
                            model.addAttribute("errorMessage", errorMessage);

                            return "resend-activation-form";
                        },
                        account -> {
                            // 활성화 이메일 재전송 성공
                            redirectAttributes.addFlashAttribute("email", email);
                            return "redirect:/resend-activation-success";
                        }
                );
    }

    @GetMapping("/resend-activation-success")
    public String showResendActivationSuccess(@RequestParam String email, Model model) {
        model.addAttribute("frontendUrl", frontendUrl);
        model.addAttribute("email", email);

        return "resend-activation-success";
    }
}
