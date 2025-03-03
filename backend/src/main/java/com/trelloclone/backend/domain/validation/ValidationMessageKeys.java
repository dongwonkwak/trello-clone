package com.trelloclone.backend.domain.validation;

public class ValidationMessageKeys {

    private ValidationMessageKeys() {
    }

    // Common validation message keys
    public static final String VALIDATION_ERROR = "validation.error";

    // Username validation message keys
    public static final String USERNAME_EMPTY = "validation.username.empty";
    public static final String USERNAME_SIZE = "validation.username.size";
    public static final String USERNAME_PATTERN = "validation.username.pattern";

    // Email validation message keys
    public static final String EMAIL_EMPTY = "validation.email.empty";
    public static final String EMAIL_INVALID = "validation.email.invalid";
    public static final String EMAIL_MAX_SIZE = "validation.email.max.size";

    // Password validation message keys
    public static final String PASSWORD_EMPTY = "validation.password.empty";
    public static final String PASSWORD_SIZE = "validation.password.size";
    public static final String PASSWORD_UPPERCASE = "validation.password.require.uppercase";
    public static final String PASSWORD_LOWERCASE = "validation.password.require.lowercase";
    public static final String PASSWORD_DIGIT = "validation.password.require.digit";
    public static final String PASSWORD_SPECIAL = "validation.password.require.special";
    public static final String PASSWORD_PATTERN = "validation.password.pattern";
}
