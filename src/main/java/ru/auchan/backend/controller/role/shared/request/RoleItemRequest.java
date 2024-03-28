package ru.auchan.backend.controller.role.shared.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.UUID;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Schema(title = "[ROLE] role item request")
public class RoleItemRequest {

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
