package ru.auchan.backend.config.exception;

import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseEntity;
import ru.auchan.backend.config.openapi.responses.ApiError400;
import ru.auchan.backend.config.openapi.responses.ApiError500;

@UtilityClass
public class ResponseEntityBuilder {

  public static ResponseEntity<Object> build(ApiError400 apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  public static ResponseEntity<Object> build(ApiError500 apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }
}
