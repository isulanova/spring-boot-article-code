package ru.auchan.backend.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ApiError {

  @JsonProperty("timestamp")
  private LocalDateTime timestamp;

  @JsonProperty("status")
  private int status;

  @JsonProperty("path")
  private String path;

  @JsonProperty("errors")
  private List<String> errors;
}
