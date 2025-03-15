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

  private @Nullable String firstName;

  private @Nullable String lastName;

  private @Nullable Link profileImage;

  public PutMeRequest firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * 사용자 이름
   * @return firstName
   */
  
  @Schema(name = "firstName", example = "John", description = "사용자 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("firstName")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public PutMeRequest lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * 사용자 성
   * @return lastName
   */
  
  @Schema(name = "lastName", example = "Doe", description = "사용자 성", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lastName")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
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
    return Objects.equals(this.firstName, putMeRequest.firstName) &&
        Objects.equals(this.lastName, putMeRequest.lastName) &&
        Objects.equals(this.profileImage, putMeRequest.profileImage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, profileImage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PutMeRequest {\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
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

