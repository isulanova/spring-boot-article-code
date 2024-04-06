package ru.auchan.backend.controller.user.shared.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.controller.role.shared.response.RoleItemResponse;
import ru.auchan.backend.io.projections.AuthUserWithRolesProj;

@Getter
@Setter
@Schema(title = "[USER] user with roles response")
public class AuthUserItemWithRolesResponse extends AuthUserItemResponse {

  @JsonProperty("roles")
  private Set<RoleItemResponse> roles;
  @Builder
  public AuthUserItemWithRolesResponse(
          final String userName, final UUID keycloakId, final Set<RoleItemResponse> roles) {
    super(userName, keycloakId);
    this.roles = roles;
  }
  public static AuthUserItemWithRolesResponse fromProjection(
          final AuthUserWithRolesProj userWithRolesProj) {
    return AuthUserItemWithRolesResponse.builder()
            .roles(userWithRolesProj.getRoles())
            .userName(userWithRolesProj.getUserName())
            .keycloakId(UUID.fromString(userWithRolesProj.getKeycloakId()))
            .build();
  }
}
