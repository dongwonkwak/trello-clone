package com.trelloclone.backend.application.port.in.board;

import com.trelloclone.backend.common.error.Failure;
import com.trelloclone.backend.domain.model.Board;
import io.vavr.control.Either;
import lombok.Builder;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.trim;

public interface CreateBoardUseCase {

    Either<Failure, Board> createBoard(CreateBoardCommand command, String email);


    @Builder
    record CreateBoardCommand(
            String title,
            String description,
            String backgroundColor,
            boolean isPublic) {

        public CreateBoardCommand(
                String title,
                String description,
                String backgroundColor,
                boolean isPublic) {
            requireNonNull(title);

            this.title = trim(title);
            this.description = trim(description);
            this.backgroundColor = backgroundColor;
            this.isPublic = isPublic;
        }


        public static final String FIELD_TITLE = "title";
        public static final String FIELD_DESCRIPTION = "description";
        public static final String FIELD_BACKGROUND_COLOR = "backgroundColor";
        public static final String FIELD_IS_PUBLIC = "isPublic";
    }


}
