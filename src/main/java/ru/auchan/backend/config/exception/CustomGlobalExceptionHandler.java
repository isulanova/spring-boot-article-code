package ru.auchan.backend.config.exception;

import static java.util.Objects.isNull;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.auchan.backend.config.openapi.responses.ApiError400;
import ru.auchan.backend.config.openapi.responses.ApiError500;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handle(ConstraintViolationException constraintViolationException) {
    Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
    List<String> details = new ArrayList<>();
    if (!violations.isEmpty()) {
      violations.forEach(violation -> details.add(violation.getMessage()));
    } else {
      details.add("ConstraintViolationException occurred");
    }

    ApiError400 err =
        new ApiError400(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST,
            HttpStatus.BAD_REQUEST.value(),
            "Argument is invalid",
            details);

    return ResponseEntityBuilder.build(err);
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleAll(Exception ex) {

    List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());

    var responseStatusAnnotation = ex.getClass().getAnnotation(ResponseStatus.class);

    ApiError500 err =
        new ApiError500(
            LocalDateTime.now(),
            isNull(responseStatusAnnotation)
                ? HttpStatus.INTERNAL_SERVER_ERROR
                : responseStatusAnnotation.value(),
            isNull(responseStatusAnnotation)
                ? HttpStatus.INTERNAL_SERVER_ERROR.value()
                : responseStatusAnnotation.value().value(),
            ex.getLocalizedMessage());

    return ResponseEntityBuilder.build(err);
  }
}
