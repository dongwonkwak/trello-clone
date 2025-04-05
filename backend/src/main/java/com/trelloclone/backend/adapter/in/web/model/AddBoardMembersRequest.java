package com.trelloclone.backend.adapter.in.web.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.trelloclone.backend.adapter.in.web.model.AddBoardMember;
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
 * a structured representation of a request to add a member to a board
 */

@Schema(name = "AddBoardMembersRequest", description = "a structured representation of a request to add a member to a board")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class AddBoardMembersRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  
  private List<AddBoardMember> data = new ArrayList<>();

  public AddBoardMembersRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AddBoardMembersRequest(List<AddBoardMember> data) {
    this.data = data;
  }

  public AddBoardMembersRequest data(List<AddBoardMember> data) {
    this.data = data;
    return this;
  }

  public AddBoardMembersRequest addDataItem(AddBoardMember dataItem) {
    if (this.data == null) {
      this.data = new ArrayList<>();
    }
    this.data.add(dataItem);
    return this;
  }

  /**
   * Get data
   * @return data
   */
  @NotNull
  @Schema(name = "data", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("data")
  public List<AddBoardMember> getData() {
    return data;
  }

  public void setData(List<AddBoardMember> data) {
    this.data = data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddBoardMembersRequest addBoardMembersRequest = (AddBoardMembersRequest) o;
    return Objects.equals(this.data, addBoardMembersRequest.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddBoardMembersRequest {\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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

