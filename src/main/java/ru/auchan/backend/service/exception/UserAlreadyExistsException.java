package ru.auchan.backend.service.exception;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {

  public UserAlreadyExistsException(final UUID id) {
    super(String.format("User with keycloak id %s already exists", id));
  }
}
