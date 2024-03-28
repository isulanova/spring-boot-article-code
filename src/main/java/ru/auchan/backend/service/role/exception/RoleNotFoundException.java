package ru.auchan.backend.service.role.exception;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RoleNotFoundException extends RuntimeException {

  public RoleNotFoundException(final String systemName) {
    super(String.format("Role with system name: %s  not found", systemName));
  }

  public RoleNotFoundException(final UUID id) {
    super(String.format("Role with id: %s  not found", id));
  }
}
