package ru.auchan.backend.controller.shared.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Schema(title = "[PERMISSION-GROUP] permission group request")
public class PermissionGroupRequest {

  @JsonProperty("name")
  @NotEmpty(message = "Please provide a name (name)")
  private String name;

  @JsonProperty("description")
  @NotEmpty(message = "Please provide a description (description)")
  private String description;

  @JsonProperty("alias")
  @NotEmpty(message = "Please provide a alias (alias)")
  private String alias;
}
