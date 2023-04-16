package ru.auchan.backend.config.openapi.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@Schema(name = "ApiError404", title = "[ERROR] 404 - Resource not found")
public class ApiError404 {

  @Schema(description = "Event timestamp", example = "08-02-2023 11:26:50")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;

  @Schema(description = "Event status", example = "NOT_FOUND")
  private HttpStatus status;

  @Schema(description = "Event code", example = "404")
  private int code;

  @Schema(description = "Event message", example = "Object x with id y not found")
  private String message;
}
