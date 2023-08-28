package ru.auchan.backend.config.exception;

import org.springframework.http.ResponseEntity;
import ru.auchan.backend.config.openapi.responses.ApiError400;
import ru.auchan.backend.config.openapi.responses.ApiError500;

public final class ResponseEntityBuilder {

  private ResponseEntityBuilder() {
    // new instance denied
  }

  public static ResponseEntity<Object> build(final ApiError400 apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  public static ResponseEntity<Object> build(final ApiError500 apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }
}
