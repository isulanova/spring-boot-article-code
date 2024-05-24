package ru.auchan.backend.service.permission.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PermissionGroupNotFoundException extends RuntimeException {

  public PermissionGroupNotFoundException(String message) {
    super(message);
  }
}
