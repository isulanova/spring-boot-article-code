package ru.auchan.backend.config.openapi.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@Schema(name = "ApiError400", title = "[ERROR] 400 - Request is invalid")
public class ApiError400 {

  @Schema(description = "Event timestamp", example = "08-02-2023 11:26:50")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;

  @Schema(description = "Event status", example = "BAD_REQUEST")
  private HttpStatus status;

  @Schema(description = "Event code", example = "400")
  private int code;

  @Schema(description = "Event message", example = "Bad request")
  private String message;

  @Schema(description = "Validation errors", example = "Some validation exceptions")
  private List<String> errors;
}
