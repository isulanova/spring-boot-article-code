package ru.auchan.backend.config.exception;

import static java.util.Objects.isNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.auchan.backend.config.openapi.responses.ApiError400;
import ru.auchan.backend.config.openapi.responses.ApiError500;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String ARGUMENT_IS_INVALID_STR = "Argument is invalid";

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
            ARGUMENT_IS_INVALID_STR,
            details);

    return ResponseEntityBuilder.build(err);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    var details =
        ex.getBindingResult().getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .distinct()
            .collect(Collectors.toCollection(ArrayList::new));

    ApiError400 err =
        new ApiError400(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST,
            HttpStatus.BAD_REQUEST.value(),
            ARGUMENT_IS_INVALID_STR,
            details);

    return ResponseEntityBuilder.build(err);
  }

  // handleMissingServletRequestParameter : triggers when there are missing parameters
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    List<String> details = new ArrayList<>();
    details.add(ex.getParameterName() + " parameter is missing");

    ApiError400 err =
        new ApiError400(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST,
            HttpStatus.BAD_REQUEST.value(),
            ARGUMENT_IS_INVALID_STR,
            details);

    return ResponseEntityBuilder.build(err);
  }

  // handleMethodArgumentTypeMismatch : triggers when a parameter's type does not match
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex, WebRequest request) {
    List<String> details = new ArrayList<>();
    details.add(ex.getMessage());

    ApiError400 err =
        new ApiError400(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST,
            HttpStatus.BAD_REQUEST.value(),
            ARGUMENT_IS_INVALID_STR,
            details);

    return ResponseEntityBuilder.build(err);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    List<String> details = new ArrayList<>();

    StringBuilder builder = new StringBuilder();
    builder.append(ex.getContentType());
    builder.append(" - media type is not supported. Supported media types are ");
    ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

    details.add(builder.toString());

    ApiError400 err =
        new ApiError400(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST,
            HttpStatus.BAD_REQUEST.value(),
            "Invalid JSON",
            details);

    return ResponseEntityBuilder.build(err);
  }

  // handleHttpMessageNotReadable : triggers when the JSON is malformed
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    List<String> details = new ArrayList<>();
    details.add(ex.getMessage());

    ApiError400 err =
        new ApiError400(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST,
            HttpStatus.BAD_REQUEST.value(),
            "Malformed JSON request",
            details);

    return ResponseEntityBuilder.build(err);
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleAll(Exception ex) {

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
