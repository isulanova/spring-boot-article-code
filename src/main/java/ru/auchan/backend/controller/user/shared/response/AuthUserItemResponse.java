package ru.auchan.backend.controller.user.shared.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthUserItemResponse {

  @JsonProperty("userName")
  private String userName;

  @JsonProperty("keycloakId")
  private UUID keycloakId;
}
