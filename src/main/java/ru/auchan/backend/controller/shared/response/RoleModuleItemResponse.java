package ru.auchan.backend.controller.shared.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "[ROLE-MODULE] role module response item")
public class RoleModuleItemResponse {

  @JsonProperty("id")
  private UUID id;

  @JsonProperty("name")
  private String name;
}
