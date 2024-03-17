package ru.auchan.backend.controller.shared.response.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.io.projection.RoleProj;

@Getter
@Setter
@Builder
@Schema(title = "[ROLE] Admin view role item response")
public class RoleItemAdminResponse {

  @JsonProperty("id")
  private UUID id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("group")
  private String group;

  @JsonProperty("systemName")
  private String systemName;

  @JsonProperty("module")
  private String module;

  @JsonProperty("functions")
  private List<String> functions = new ArrayList<>();

  public static RoleItemAdminResponse fromProjection(final RoleProj roleProj) {
    return RoleItemAdminResponse.builder()
            .id(UUID.fromString(roleProj.getId()))
            .name(roleProj.getLabel())
            .systemName(roleProj.getName())
            .group(roleProj.getGroup())
            .module(roleProj.getModule())
            .functions(roleProj.getFunctions())
            .build();
  }

}
