package com.trelloclone.backend.infrastructure.config;

import com.trelloclone.backend.adapter.out.persistence.entity.AccountEntity;
import com.trelloclone.backend.adapter.out.persistence.repository.AccountRepository;
import com.trelloclone.backend.application.port.out.token.TokenPort;
import com.trelloclone.backend.domain.model.Id;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
public class DevDataInitializer implements ApplicationRunner {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenPort tokenPort;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var accounts = createAccounts();
        accounts.forEach(account -> {
            tokenPort.createActivationToken(Id.of(account.getId()), LocalDateTime.now().plusHours(24))
                    .map(token -> {
                       var link = "http://localhost:8080/activate?token=" + token;
                       log.info("[Dev] Activation link for {}: {}", account.getEmail(), link);
                       return link;
                    });
        });
    }

    private AccountEntity createAccount(
            String email, String password,
            String firstName, String lastName,
            boolean emailVerified) {
        log.info("[Dev] Create account: email={}", email);

        var account = new AccountEntity();
        account.setEmail(email);
        account.setPassword(passwordEncoder.encode(password));
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmailVerified(emailVerified);

        return accountRepository.save(account);
    }

    private List<AccountEntity> createAccounts() {
        List<AccountEntity> accounts = new ArrayList<>();
        accounts.add(createAccount(
                "test1@example.com",
                "Password1!",
                "Test1",
                "User1", true));
        accounts.add(createAccount(
                "test2@example.com",
                "Password1!",
                "Test2",
                "User2", false));

        return accounts;
    }
}
