package ru.auchan.backend.controller.shared.request.permission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Schema(title = "[PERMISSION] Permission item request")
public class PermissionItemRequest {

  @JsonProperty("systemName")
  @NotEmpty(message = "Please provide a systemName (systemName)")
  private String systemName;

  @JsonProperty("uiName")
  @NotEmpty(message = "Please provide a uiName (uiName)")
  private String uiName;

  @JsonProperty("description")
  @NotEmpty(message = "Please provide a description (description)")
  private String description;
}
