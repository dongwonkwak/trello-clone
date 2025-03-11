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
 * a link to further details about this particular occurrence of the problem
 */

@Schema(name = "Error_errors_inner_links", description = "a link to further details about this particular occurrence of the problem")
@JsonTypeName("Error_errors_inner_links")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class ErrorErrorsInnerLinks implements Serializable {

  private static final long serialVersionUID = 1L;

  private @Nullable Link about;

  public ErrorErrorsInnerLinks about(Link about) {
    this.about = about;
    return this;
  }

  /**
   * Get about
   * @return about
   */
  
  @Schema(name = "about", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("about")
  public Link getAbout() {
    return about;
  }

  public void setAbout(Link about) {
    this.about = about;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorErrorsInnerLinks errorErrorsInnerLinks = (ErrorErrorsInnerLinks) o;
    return Objects.equals(this.about, errorErrorsInnerLinks.about);
  }

  @Override
  public int hashCode() {
    return Objects.hash(about);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorErrorsInnerLinks {\n");
    sb.append("    about: ").append(toIndentedString(about)).append("\n");
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

