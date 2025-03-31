package com.trelloclone.backend.domain.validation;

import com.trelloclone.backend.application.port.in.board.CreateBoardUseCase.CreateBoardCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardValidatorTest {
    private BoardValidator validator;

    @BeforeEach
    void setUp() {
        validator = new BoardValidator();
    }

    // Title validation tests
    @Test
    @DisplayName("제목이 null인 경우 유효성 검사 실패")
    void validate_whenTitleIsNull_shouldReturnInvalid() {
        assertThat(assertThrows(NullPointerException.class, () ->
                new CreateBoardCommand(null, "Description", "#FFFFFF", true)))
                .isNotNull();
    }

    @Test
    @DisplayName("제목이 빈 문자열인 경우 유효성 검사 실패")
    void validate_whenTitleIsEmpty_shouldReturnInvalid() {
        var command = new CreateBoardCommand("", "Description", "#FFFFFF", true);
        var result = validator.validate(command);

        assertThat(result.isInvalid()).isTrue();
        assertThat(result.getError().get(0).field()).isEqualTo(CreateBoardCommand.FIELD_TITLE);
        assertThat(result.getError().get(0).message()).isEqualTo(ValidationMessageKeys.TITLE_EMPTY);
    }

    @Test
    @DisplayName("제목이 최대 길이를 초과하는 경우 유효성 검사 실패")
    void validate_whenTitleIsTooLong_shouldReturnInvalid() {
        var longTitle = "a".repeat(101);
        var command = new CreateBoardCommand(longTitle, "Description", null, true);
        var result = validator.validate(command);

        assertThat(result.isInvalid()).isTrue();
        assertThat(result.getError().get(0).field()).isEqualTo(CreateBoardCommand.FIELD_TITLE);
        assertThat(result.getError().get(0).message()).isEqualTo(ValidationMessageKeys.TITLE_SIZE);
    }

    // Description validation tests
    @Test
    @DisplayName("설명이 null인 경우 유효성 검사 통과")
    void validate_whenDescriptionIsNull_shouldReturnValid() {
        var command = new CreateBoardCommand("Valid Title", null, "#FFFFFF", true);
        var result = validator.validate(command);

        assertThat(result.isValid()).isTrue();
    }

    @Test
    @DisplayName("설명이 최대 길이를 초과하는 경우 유효성 검사 실패")
    void validate_whenDescriptionIsTooLong_shouldReturnInvalid() {
        var longDescription = "a".repeat(501);
        var command = new CreateBoardCommand("Valid Title", longDescription, "#FFFFFF", true);
        var result = validator.validate(command);

        assertThat(result.isInvalid()).isTrue();
        assertThat(result.getError().get(0).field()).isEqualTo(CreateBoardCommand.FIELD_DESCRIPTION);
        assertThat(result.getError().get(0).message()).isEqualTo(ValidationMessageKeys.DESCRIPTION_SIZE);
    }

    // Background color validation tests
    @Test
    @DisplayName("배경색이 null인 경우 유효성 검사 통과")
    void validate_whenBackgroundColorIsNull_shouldReturnValid() {
        var command = new CreateBoardCommand("Valid Title", "Description", null, true);
        var result = validator.validate(command);

        assertThat(result.isValid()).isTrue();
    }

    @Test
    @DisplayName("배경색 형식이 유효하지 않은 경우 유효성 검사 실패")
    void validate_whenBackgroundColorIsInvalid_shouldReturnInvalid() {
        var command = new CreateBoardCommand("Valid Title", "Description", "invalid-color", true);
        var result = validator.validate(command);

        assertThat(result.isInvalid()).isTrue();
        assertThat(result.getError().get(0).field()).isEqualTo(CreateBoardCommand.FIELD_BACKGROUND_COLOR);
        assertThat(result.getError().get(0).message()).isEqualTo(ValidationMessageKeys.BACKGROUND_COLOR_INVALID);
    }

    @Test
    @DisplayName("짧은 헥스 컬러 코드(#FFF)인 경우 유효성 검사 통과")
    void validate_whenShortHexColor_shouldReturnValid() {
        var command = new CreateBoardCommand("Valid Title", "Description", "#FFF", true);
        var result = validator.validate(command);

        assertThat(result.isValid()).isTrue();
    }

    @Test
    @DisplayName("긴 헥스 컬러 코드(#FFFFFF)인 경우 유효성 검사 통과")
    void validate_whenLongHexColor_shouldReturnValid() {
        var command = new CreateBoardCommand("Valid Title", "Description", "#FFFFFF", true);
        var result = validator.validate(command);

        assertThat(result.isValid()).isTrue();
    }

    // Combined validation tests
    @Test
    @DisplayName("모든 필드가 유효한 경우 유효성 검사 통과")
    void validate_whenAllFieldsValid_shouldReturnValid() {
        var command = new CreateBoardCommand("Valid Title", "Valid Description", "#FFFFFF", true);
        var result = validator.validate(command);

        assertThat(result.isValid()).isTrue();
        assertThat(result.get()).isEqualTo(command);
    }

    @Test
    @DisplayName("여러 필드가 유효하지 않은 경우 모든 오류 반환")
    void validate_whenMultipleInvalidFields_shouldReturnAllErrors() {
        var command = new CreateBoardCommand("", "a".repeat(501), "invalid-color", true);
        var result = validator.validate(command);

        assertThat(result.isInvalid()).isTrue();
        assertThat(result.getError().size()).isEqualTo(3);

        assertThat(result.getError().find(v -> v.field().equals(CreateBoardCommand.FIELD_TITLE))).isNotEmpty();
        assertThat(result.getError().find(v -> v.field().equals(CreateBoardCommand.FIELD_DESCRIPTION))).isNotEmpty();
        assertThat(result.getError().find(v -> v.field().equals(CreateBoardCommand.FIELD_BACKGROUND_COLOR))).isNotEmpty();
    }
}
