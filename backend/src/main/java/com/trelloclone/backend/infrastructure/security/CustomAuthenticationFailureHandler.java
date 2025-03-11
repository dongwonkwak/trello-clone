package com.trelloclone.backend.infrastructure.security;

import com.trelloclone.backend.domain.validation.ErrorMessageKeys;
import com.trelloclone.backend.infrastructure.MessageGetter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final MessageGetter messageGetter;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = switch (exception.getClass().getSimpleName()) {
            case "UsernameNotFoundException" -> messageGetter.getMessage(ErrorMessageKeys.ACCOUNT_WITH_EMAIL_NOT_FOUND);
            case "DisabledException" -> messageGetter.getMessage(ErrorMessageKeys.ACCOUNT_NOT_VERIFIED);
            case "BadCredentialsException" -> messageGetter.getMessage(ErrorMessageKeys.ACCOUNT_INCORRECT_PASSWORD);
            default -> exception.getClass().getSimpleName();
        };

        setDefaultFailureUrl("/login?error=true");
        request.getSession().setAttribute("errorMessage", errorMessage);

        super.onAuthenticationFailure(request, response, exception);
    }
}
