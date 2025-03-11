package com.trelloclone.backend.adapter.in.web.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.trelloclone.backend.adapter.in.web.model.ErrorErrorsInner;
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
 * A JSON:API-compliant error response. This object is inherited by the specific response type.
 */

@Schema(name = "Error", description = "A JSON:API-compliant error response. This object is inherited by the specific response type.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class Error implements Serializable {

  private static final long serialVersionUID = 1L;

  
  private List<ErrorErrorsInner> errors = new ArrayList<>();

  public Error() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Error(List<ErrorErrorsInner> errors) {
    this.errors = errors;
  }

  public Error errors(List<ErrorErrorsInner> errors) {
    this.errors = errors;
    return this;
  }

  public Error addErrorsItem(ErrorErrorsInner errorsItem) {
    if (this.errors == null) {
      this.errors = new ArrayList<>();
    }
    this.errors.add(errorsItem);
    return this;
  }

  /**
   * An array of Error objects
   * @return errors
   */
  @NotNull
  @Schema(name = "errors", description = "An array of Error objects", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("errors")
  public List<ErrorErrorsInner> getErrors() {
    return errors;
  }

  public void setErrors(List<ErrorErrorsInner> errors) {
    this.errors = errors;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Error error = (Error) o;
    return Objects.equals(this.errors, error.errors);
  }

  @Override
  public int hashCode() {
    return Objects.hash(errors);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Error {\n");
    sb.append("    errors: ").append(toIndentedString(errors)).append("\n");
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

