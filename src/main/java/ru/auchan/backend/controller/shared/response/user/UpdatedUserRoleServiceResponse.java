package ru.auchan.backend.controller.shared.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.controller.shared.response.role.RoleSimpleItemResponse;

@Getter
@Setter
@Schema(title = "[USER] user roles updated response")
public class UpdatedUserRoleServiceResponse {

  @JsonProperty("userName")
  private String userName;

  @JsonProperty("keycloakId")
  private UUID keycloakId;

  @JsonProperty("currentRoles")
  private Set<RoleSimpleItemResponse> currentRoles = new HashSet<>();

  @JsonProperty("modifiedRoles")
  private Set<RoleSimpleItemResponse> modifiedRoles = new HashSet<>();
}
