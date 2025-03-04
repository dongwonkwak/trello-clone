package com.trelloclone.backend.application.service.email;

import com.trelloclone.backend.application.port.out.email.EmailPort;
import com.trelloclone.backend.domain.domain.AccountCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountEmailListener {

    private final EmailPort emailPort;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAccountCreatedEvent(AccountCreatedEvent event) {
        log.info("Account created event: {}", event);

        emailPort.sendAccountActivationEmail(
                event.email(),
                event.username(),
                event.activationToken(),
                event.locale()
        ).fold(
                failure -> {
                    log.error("Failed to send activation email: {}", failure.message());
                    return null;
                },
                success -> {
                    log.info("Successfully sent activation email to: {}", event.email());
                    return null;
                }
        );
    }
}
