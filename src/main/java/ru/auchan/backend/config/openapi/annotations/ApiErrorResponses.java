package ru.auchan.backend.config.openapi.annotations;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(
    value = {
      @ApiResponse(
          responseCode = "400",
          content =
              @Content(mediaType = "application/json", schema = @Schema(ref = "ApiError400"))),
      @ApiResponse(
          responseCode = "401",
          content =
              @Content(mediaType = "application/json", schema = @Schema(ref = "ApiError401"))),
      @ApiResponse(
          responseCode = "404",
          content =
              @Content(mediaType = "application/json", schema = @Schema(ref = "ApiError404"))),
      @ApiResponse(
          responseCode = "409",
          content =
              @Content(mediaType = "application/json", schema = @Schema(ref = "ApiError409"))),
      @ApiResponse(
          responseCode = "500",
          content = @Content(mediaType = "application/json", schema = @Schema(ref = "ApiError500")))
    })
public @interface ApiErrorResponses {}
