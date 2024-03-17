package ru.auchan.backend.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ApplyRoleModelException extends RuntimeException {
  public ApplyRoleModelException(final String message) {
    super(message);
  }
}
