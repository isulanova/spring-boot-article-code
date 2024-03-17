package ru.auchan.backend.service.exception;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PermissionNotFoundException extends RuntimeException {

  public PermissionNotFoundException(final UUID id) {
    super(String.format("Permission with id: %s not found", id));
  }
}
