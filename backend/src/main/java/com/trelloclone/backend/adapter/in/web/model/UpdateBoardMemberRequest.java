package com.trelloclone.backend.adapter.in.web.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * a structured representation of a request to update a board member
 */

@Schema(name = "UpdateBoardMemberRequest", description = "a structured representation of a request to update a board member")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class UpdateBoardMemberRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 멤버 권한
   */
  public enum RoleEnum {
    ADMIN("ADMIN"),
    
    MEMBER("MEMBER"),
    
    VIEWER("VIEWER");

    private String value;

    RoleEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static RoleEnum fromValue(String value) {
      for (RoleEnum b : RoleEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private RoleEnum role;

  public UpdateBoardMemberRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UpdateBoardMemberRequest(RoleEnum role) {
    this.role = role;
  }

  public UpdateBoardMemberRequest role(RoleEnum role) {
    this.role = role;
    return this;
  }

  /**
   * 멤버 권한
   * @return role
   */
  @NotNull
  @Schema(name = "role", example = "MEMBER", description = "멤버 권한", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("role")
  public RoleEnum getRole() {
    return role;
  }

  public void setRole(RoleEnum role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateBoardMemberRequest updateBoardMemberRequest = (UpdateBoardMemberRequest) o;
    return Objects.equals(this.role, updateBoardMemberRequest.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(role);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateBoardMemberRequest {\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
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

