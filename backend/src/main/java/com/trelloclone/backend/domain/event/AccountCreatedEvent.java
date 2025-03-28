package com.trelloclone.backend.domain.event;

import lombok.Builder;

import java.util.Locale;

@Builder
public record AccountCreatedEvent(
        String email,
        String activationToken,
        Locale locale) {
}
