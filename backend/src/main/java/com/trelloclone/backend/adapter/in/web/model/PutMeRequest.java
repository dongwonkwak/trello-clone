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

  private @Nullable String firstname;

  private @Nullable String lastname;

  private @Nullable Link profileImage;

  public PutMeRequest firstname(String firstname) {
    this.firstname = firstname;
    return this;
  }

  /**
   * 사용자 이름
   * @return firstname
   */
  
  @Schema(name = "firstname", example = "John", description = "사용자 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("firstname")
  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public PutMeRequest lastname(String lastname) {
    this.lastname = lastname;
    return this;
  }

  /**
   * 사용자 성
   * @return lastname
   */
  
  @Schema(name = "lastname", example = "Doe", description = "사용자 성", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lastname")
  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
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
    return Objects.equals(this.firstname, putMeRequest.firstname) &&
        Objects.equals(this.lastname, putMeRequest.lastname) &&
        Objects.equals(this.profileImage, putMeRequest.profileImage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstname, lastname, profileImage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PutMeRequest {\n");
    sb.append("    firstname: ").append(toIndentedString(firstname)).append("\n");
    sb.append("    lastname: ").append(toIndentedString(lastname)).append("\n");
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

