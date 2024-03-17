package ru.auchan.backend.controller.shared.response.user;

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
