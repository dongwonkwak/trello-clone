package com.trelloclone.backend.adapter.in.web.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.trelloclone.backend.adapter.in.web.model.BoardResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * a structured representation of a list of boards
 */

@Schema(name = "BoardsResponse", description = "a structured representation of a list of boards")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class BoardsResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  
  private List<BoardResponse> boards = new ArrayList<>();

  public BoardsResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public BoardsResponse(List<BoardResponse> boards) {
    this.boards = boards;
  }

  public BoardsResponse boards(List<BoardResponse> boards) {
    this.boards = boards;
    return this;
  }

  public BoardsResponse addBoardsItem(BoardResponse boardsItem) {
    if (this.boards == null) {
      this.boards = new ArrayList<>();
    }
    this.boards.add(boardsItem);
    return this;
  }

  /**
   * Get boards
   * @return boards
   */
  @NotNull
  @Schema(name = "boards", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("boards")
  public List<BoardResponse> getBoards() {
    return boards;
  }

  public void setBoards(List<BoardResponse> boards) {
    this.boards = boards;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BoardsResponse boardsResponse = (BoardsResponse) o;
    return Objects.equals(this.boards, boardsResponse.boards);
  }

  @Override
  public int hashCode() {
    return Objects.hash(boards);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BoardsResponse {\n");
    sb.append("    boards: ").append(toIndentedString(boards)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

