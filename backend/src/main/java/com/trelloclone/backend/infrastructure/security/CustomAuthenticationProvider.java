package com.trelloclone.backend.infrastructure.security;

import com.trelloclone.backend.application.port.out.account.AccountPort;
import com.trelloclone.backend.common.error.Failure;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AccountPort accountPort;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        var result = accountPort.findAccountByEmail(email)
                .fold(failure -> {
                    if (failure instanceof Failure.NotFoundFailure) {
                        throw new UsernameNotFoundException("Account not found");
                    }
                    throw new InternalException("Internal server error");
                }, account -> {
                    if (!account.isEmailVerified()) {
                        throw new DisabledException("Your account is not activated yet");
                    }
                    if (!encoder.matches(password, account.getPassword())) {
                        throw new BadCredentialsException("Incorrect password");
                    }
                    return account;
                });

        var userDetails = new AccountUserDetails(result);

        return new UsernamePasswordAuthenticationToken(
                result,
                userDetails,
                userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
