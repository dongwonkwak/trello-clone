package com.trelloclone.backend.adapter.in.web.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.trelloclone.backend.adapter.in.web.model.Link;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * PutMeRequest
 */

@JsonTypeName("putMe_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class PutMeRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private @Nullable String fullName;

  private @Nullable Link profileImage;

  public PutMeRequest fullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  /**
   * 사용자의 전체 이름
   * @return fullName
   */
  
  @Schema(name = "fullName", example = "John Doe", description = "사용자의 전체 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fullName")
  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public PutMeRequest profileImage(Link profileImage) {
    this.profileImage = profileImage;
    return this;
  }

  /**
   * Get profileImage
   * @return profileImage
   */
  
  @Schema(name = "profileImage", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("profileImage")
  public Link getProfileImage() {
    return profileImage;
  }

  public void setProfileImage(Link profileImage) {
    this.profileImage = profileImage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PutMeRequest putMeRequest = (PutMeRequest) o;
    return Objects.equals(this.fullName, putMeRequest.fullName) &&
        Objects.equals(this.profileImage, putMeRequest.profileImage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fullName, profileImage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PutMeRequest {\n");
    sb.append("    fullName: ").append(toIndentedString(fullName)).append("\n");
    sb.append("    profileImage: ").append(toIndentedString(profileImage)).append("\n");
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

