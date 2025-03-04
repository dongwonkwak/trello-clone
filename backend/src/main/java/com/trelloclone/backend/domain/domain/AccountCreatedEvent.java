package com.trelloclone.backend.domain.domain;

import lombok.Builder;

import java.util.Locale;

@Builder
public record AccountCreatedEvent(
        String email,
        String username,
        String activationToken,
        Locale locale) {
}
