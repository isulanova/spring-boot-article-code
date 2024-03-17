package ru.auchan.backend.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class PermissionAlreadyExistsException extends RuntimeException {

  public PermissionAlreadyExistsException(final String name) {
    super(String.format("Permission with name %s already exists", name));
  }
}
