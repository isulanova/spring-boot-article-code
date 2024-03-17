package ru.auchan.backend.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class RoleAlreadyExistsException extends RuntimeException {

  public RoleAlreadyExistsException(final String systemName) {
    super(String.format("Role with system name %s already exists", systemName));
  }
}
