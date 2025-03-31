package com.trelloclone.backend.domain.validation;

import com.trelloclone.backend.application.port.in.board.CreateBoardUseCase.CreateBoardCommand;
import com.trelloclone.backend.common.error.Failure.FieldViolation;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isBlank;

import static io.vavr.API.*;

@Component
public final class BoardValidator {
    private static final String HEX_COLOR_PATTERN = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
    private static final int TITLE_MAX_LENGTH = 100;
    private static final int DESCRIPTION_MAX_LENGTH = 500;

    public Validation<Seq<FieldViolation>, CreateBoardCommand> validate(CreateBoardCommand command) {
        return Validation.combine(
                validateTitle(command.title()),
                validateDescription(command.description()),
                validateBackgroundColor(command.backgroundColor())
        ).ap((title, description, backgroundColor) -> command);
    }

    private Validation<FieldViolation, String> validateTitle(String title) {
        if (isBlank(title)) {
            return Invalid(
                    FieldViolation.builder()
                            .field(CreateBoardCommand.FIELD_TITLE)
                            .message(ValidationMessageKeys.TITLE_EMPTY)
                            .build());
        }

        if (title.length() > TITLE_MAX_LENGTH) {
            return Invalid(
                    FieldViolation.builder()
                            .field(CreateBoardCommand.FIELD_TITLE)
                            .message(ValidationMessageKeys.TITLE_SIZE)
                            .args(new Object[]{1, TITLE_MAX_LENGTH})
                            .rejectedValue(title)
                            .build());
        }

        return Valid(title);
    }

    private Validation<FieldViolation, String> validateDescription(String description) {
        if (description != null && description.length() > DESCRIPTION_MAX_LENGTH) {
            return Invalid(
                    FieldViolation.builder()
                            .field(CreateBoardCommand.FIELD_DESCRIPTION)
                            .message(ValidationMessageKeys.DESCRIPTION_SIZE)
                            .args(new Object[]{DESCRIPTION_MAX_LENGTH})
                            .rejectedValue(description)
                            .build());
        }

        return Valid(description);
    }

    private Validation<FieldViolation, String> validateBackgroundColor(String backgroundColor) {
        if (backgroundColor != null && !Pattern.compile(HEX_COLOR_PATTERN).matcher(backgroundColor).matches()) {
            return Invalid(
                    FieldViolation.builder()
                            .field(CreateBoardCommand.FIELD_BACKGROUND_COLOR)
                            .message(ValidationMessageKeys.BACKGROUND_COLOR_INVALID)
                            .rejectedValue(backgroundColor)
                            .build());
        }

        return Valid(backgroundColor);
    }
}
