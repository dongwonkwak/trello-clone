package com.trelloclone.backend.infrastructure;

import io.vavr.control.Try;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageGetter {
    private final MessageSource messageSource;

    public MessageGetter(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String code, Object[] args) {
        return Try.of(() -> messageSource.getMessage(code, args, LocaleContextHolder.getLocale()))
                .getOrElse(code);
    }

    public String getMessage(String code) {
        return this.getMessage(code, null);
    }
}
