package ru.auchan.backend.controller.user.shared.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthUserItemResponse {

  @JsonProperty("userName")
  private String userName;

  @JsonProperty("keycloakId")
  private UUID keycloakId;
}
