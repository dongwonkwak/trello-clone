package com.trelloclone.backend.common.error;

import jakarta.annotation.Nullable;
import lombok.Builder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public interface Failure {
    String message();

    static Failure ofValidation(String message, Collection<FieldViolation> fieldViolations) {
        return new ValidationFailure(message, fieldViolations);
    }

    static Failure ofNotFound(String message) {
        return new NotFoundFailure(message);
    }

    static Failure ofConflict(String message) {
        return new ConflictFailure(message);
    }

    static Failure ofForbidden(String message) {
        return new ForbiddenFailure(message);
    }

    static Failure ofBadRequest(String message) {
        return new IllegalFailure(message);
    }

    static Failure ofInternalServerError(String message) {
        return new InternalServerError(message);
    }

    record ValidationFailure(String message, Collection<FieldViolation> fieldViolations) implements Failure {
        public ValidationFailure {
            requireNonNull(fieldViolations, "fieldViolations must not be null");
        }
    }

    record IllegalFailure(String message) implements Failure {
    }

    record NotFoundFailure(String message) implements Failure {
    }

    record ConflictFailure(String message) implements Failure {
    }

    record ForbiddenFailure(String message) implements Failure {
    }

    record InternalServerError(String message) implements Failure {
    }

    @Builder
    record FieldViolation(String field, String message, Object rejectedValue, @Nullable Object... args) {
        public FieldViolation {
            requireNonNull(field, "field must not be null");
            requireNonNull(message, "message must not be null");
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            FieldViolation that = (FieldViolation) obj;

            return field.equals(that.field) &&
                    message.equals(that.message) &&
                    rejectedValue.equals(that.rejectedValue)
                    && Arrays.equals(args, that.args);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(field, message, rejectedValue);
            result = 31 * result + Arrays.hashCode(args);
            return result;
        }

        @Override
        public String toString() {
            return "FieldViolation{" +
                    "field='" + field + '\'' +
                    ", message='" + message + '\'' +
                    ", rejectedValue=" + rejectedValue +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
