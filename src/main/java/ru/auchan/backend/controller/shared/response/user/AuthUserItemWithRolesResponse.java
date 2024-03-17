package ru.auchan.backend.controller.shared.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.controller.shared.response.role.RoleItemResponse;

@Getter
@Setter
@Schema(title = "[USER] user with roles response")
public class AuthUserItemWithRolesResponse extends AuthUserItemResponse {

  @JsonProperty("roles")
  private Set<RoleItemResponse> roles;
}
