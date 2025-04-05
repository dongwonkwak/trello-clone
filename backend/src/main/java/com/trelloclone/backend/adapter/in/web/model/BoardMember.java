package com.trelloclone.backend.adapter.in.web.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.trelloclone.backend.adapter.in.web.model.AccountResponse;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * a structured representation of a board member
 */

@Schema(name = "BoardMember", description = "a structured representation of a board member")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class BoardMember implements Serializable {

  private static final long serialVersionUID = 1L;

  private AccountResponse account;

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

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime joinedAt;

  public BoardMember() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public BoardMember(AccountResponse account, RoleEnum role, LocalDateTime joinedAt) {
    this.account = account;
    this.role = role;
    this.joinedAt = joinedAt;
  }

  public BoardMember account(AccountResponse account) {
    this.account = account;
    return this;
  }

  /**
   * Get account
   * @return account
   */
  @NotNull
  @Schema(name = "account", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("account")
  public AccountResponse getAccount() {
    return account;
  }

  public void setAccount(AccountResponse account) {
    this.account = account;
  }

  public BoardMember role(RoleEnum role) {
    this.role = role;
    return this;
  }

  /**
   * 멤버 권한
   * @return role
   */
  @NotNull
  @Schema(name = "role", description = "멤버 권한", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("role")
  public RoleEnum getRole() {
    return role;
  }

  public void setRole(RoleEnum role) {
    this.role = role;
  }

  public BoardMember joinedAt(LocalDateTime joinedAt) {
    this.joinedAt = joinedAt;
    return this;
  }

  /**
   * 멤버 가입일
   * @return joinedAt
   */
  @NotNull
  @Schema(name = "joinedAt", example = "2023-10-01T12:00Z", description = "멤버 가입일", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("joinedAt")
  public LocalDateTime getJoinedAt() {
    return joinedAt;
  }

  public void setJoinedAt(LocalDateTime joinedAt) {
    this.joinedAt = joinedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BoardMember boardMember = (BoardMember) o;
    return Objects.equals(this.account, boardMember.account) &&
        Objects.equals(this.role, boardMember.role) &&
        Objects.equals(this.joinedAt, boardMember.joinedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(account, role, joinedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BoardMember {\n");
    sb.append("    account: ").append(toIndentedString(account)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    joinedAt: ").append(toIndentedString(joinedAt)).append("\n");
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

