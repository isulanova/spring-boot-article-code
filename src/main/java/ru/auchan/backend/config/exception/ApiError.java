package ru.auchan.backend.config.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

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
