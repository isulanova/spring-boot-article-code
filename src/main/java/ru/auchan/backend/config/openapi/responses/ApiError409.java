package ru.auchan.backend.config.openapi.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Schema(
        name = "ApiError409",
        title = "[ERROR] 409 - Resource already exists"
)
public class ApiError409 {

    @Schema(description = "Event timestamp", example = "08-02-2023 11:26:50")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    @Schema(description = "Event status", example = "CONFLICT")
    private HttpStatus status;

    @Schema(description = "Event code", example = "409")
    private int code;

    @Schema(description = "Event message", example = "Object x already exist")
    private String message;

}
