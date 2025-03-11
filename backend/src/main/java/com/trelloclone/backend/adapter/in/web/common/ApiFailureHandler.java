package com.trelloclone.backend.adapter.in.web.common;

import com.trelloclone.backend.adapter.in.web.model.Error;
import com.trelloclone.backend.adapter.in.web.model.ErrorErrorsInner;
import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.infrastructure.MessageGetter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.trelloclone.backend.common.error.Failure.*;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Component
@RequiredArgsConstructor
public final class ApiFailureHandler {

    private final MessageGetter messageGetter;

    public ResponseEntity<?> handle(Failure failure) {
        return switch (failure) {
            case IllegalFailure illegalFailure -> badRequest(illegalFailure);
            case ForbiddenFailure forbiddenFailure -> forbidden(forbiddenFailure);
            case NotFoundFailure notFoundFailure -> notFound(notFoundFailure);
            case ValidationFailure validationFailure -> unprocessable(validationFailure);
            case ConflictFailure conflictFailure -> conflict(conflictFailure);
            case InternalServerError internalServerError -> internalServerError(internalServerError);
            default -> ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        };
    }

    // 400
    private ResponseEntity<Error> badRequest(IllegalFailure failure) {
        var error = new Error()
                .errors(Collections.singletonList(new ErrorErrorsInner()
                        .title(failure.message())
                        .status(BAD_REQUEST.value())
                ));
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

    // 401
    private ResponseEntity<Error> unauthorized(ForbiddenFailure failure) {
        var error = new Error()
                .errors(Collections.singletonList(new ErrorErrorsInner()
                        .title(failure.message())
                        .status(UNAUTHORIZED.value())
                ));
        return ResponseEntity.status(UNAUTHORIZED).body(error);
    }

    // 403
    private ResponseEntity<Error> forbidden(ForbiddenFailure failure) {
        var error = new Error()
                .errors(Collections.singletonList(new ErrorErrorsInner()
                        .title(failure.message())
                        .status(FORBIDDEN.value())
                ));
        return ResponseEntity.status(FORBIDDEN).body(error);
    }

    // 404
    private ResponseEntity<Error> notFound(NotFoundFailure failure) {
        var error = new Error()
                .errors(Collections.singletonList(new ErrorErrorsInner()
                        .title(failure.message())
                        .status(NOT_FOUND.value())
                ));
        return ResponseEntity.status(NOT_FOUND).body(error);
    }

    // 409
    private ResponseEntity<Error> conflict(ConflictFailure failure) {
        var error = new Error()
                .errors(Collections.singletonList(new ErrorErrorsInner()
                        .title(failure.message())
                        .status(CONFLICT.value())
                ));
        return ResponseEntity.status(CONFLICT).body(error);
    }

    // 422
    private ResponseEntity<Error> unprocessable(ValidationFailure failure) {
        var error = new Error()
                .errors(toValidationErrors(failure.fieldViolations()));
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(error);
    }

    private List<ErrorErrorsInner> toValidationErrors(Collection<FieldViolation> fieldViolationCollections) {
        return fieldViolationCollections.stream()
                .map(violation -> new ErrorErrorsInner()
                        .status(UNPROCESSABLE_ENTITY.value())
                        .title(messageGetter.getMessage(violation.message(), violation.args()))
                ).toList();
    }

    // 500
    private ResponseEntity<Error> internalServerError(InternalServerError failure) {
        var error = new Error()
                .errors(Collections.singletonList(new ErrorErrorsInner()
                        .title(failure.message())
                        .status(INTERNAL_SERVER_ERROR.value())
                ));
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(error);
    }
}
