package ru.auchan.backend.controller.access.shared.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(title = "[ACCESS] User access map")
public class UserAccessMapResponse implements Serializable {

  @JsonProperty("id")
  private String id;

  @JsonProperty("permissions")
  private List<String> permissions;

  @JsonProperty("roles")
  private List<String> userRoles;
}
