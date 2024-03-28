package ru.auchan.backend.service.user.exception;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(final UUID id) {
    super(String.format("User with keycloak ID: %s not found", id));
  }
}
