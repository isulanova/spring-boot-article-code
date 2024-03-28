package ru.auchan.backend.service.role.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class RoleModelException extends RuntimeException {

  public RoleModelException(final String message) {
    super(message);
  }
}
