package ru.auchan.backend.controller.role.shared.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.io.projection.RoleByKeycloakIdProj;

@Setter
@Getter
@Builder
@Schema(title = "[ROLE] roles by keycloak id response")
public class RolesByUserResponse implements Serializable {

  @JsonProperty("id")
  private UUID keycloakId;

  @JsonProperty("roles")
  private List<RoleSimpleItemResponse> roles;

  public static RolesByUserResponse fromProjection(final RoleByKeycloakIdProj proj) {
    return RolesByUserResponse.builder()
            .keycloakId(UUID.fromString(proj.getId()))
            .roles(proj.getRoles())
            .build();
  }
}
