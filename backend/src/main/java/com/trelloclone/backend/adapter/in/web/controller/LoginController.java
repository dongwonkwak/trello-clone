package com.trelloclone.backend.adapter.in.web.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Value("${application.frontend-url}")
    private String frontendUrl;

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                        Model model,
                        HttpSession session) {
        model.addAttribute("frontendUrl", frontendUrl);
        if (error != null) {
            model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
            session.removeAttribute("errorMessage");
        }

        return "login";
    }
}
