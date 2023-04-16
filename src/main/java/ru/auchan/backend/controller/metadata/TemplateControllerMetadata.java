package ru.auchan.backend.controller.metadata;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.auchan.backend.config.openapi.annotations.ApiErrorResponses;
import ru.auchan.backend.config.openapi.responses.TemplatePageableResponse;
import ru.auchan.backend.controller.shared.request.TemplatePageableRequest;
import ru.auchan.backend.controller.shared.request.TemplateRequest;
import ru.auchan.backend.controller.shared.response.PageableResponse;
import ru.auchan.backend.controller.shared.response.TemplateResponse;

@Tag(name = "[TEMPLATE] Template API", description = "API Description")
@RequestMapping("/api/template")
public interface TemplateControllerMetadata {

  @Operation(summary = "Add item description")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Success",
            content = {
              @Content(
                  mediaType = APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = TemplateResponse.class))
            })
      })
  @ApiErrorResponses
  @ResponseStatus(HttpStatus.ACCEPTED)
  @PostMapping(value = "/v1", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  ResponseEntity<TemplateResponse> add(@Valid @RequestBody TemplateRequest request);

  @Operation(summary = "Delete item description")
  @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Success")})
  @ApiErrorResponses
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping(value = "/v1/{itemId}")
  void removeById(@PathVariable("itemId") @NotNull UUID itemId);

  @Operation(summary = "Update item description")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "202",
            description = "Success",
            content = {
              @Content(
                  mediaType = APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = TemplateResponse.class))
            })
      })
  @ApiErrorResponses
  @PutMapping(
      value = "/v1/{itemId}",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  ResponseEntity<TemplateResponse> update(
      @PathVariable("itemId") UUID id, @Valid @RequestBody TemplateRequest request);

  @Operation(summary = "Find by id description")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = {
              @Content(
                  mediaType = APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = TemplateResponse.class))
            })
      })
  @ApiErrorResponses
  @GetMapping(value = "/v1/{itemId}", produces = APPLICATION_JSON_VALUE)
  ResponseEntity<TemplateResponse> findById(@PathVariable("itemId") UUID itemId);

  @Operation(summary = "Find with pageable and filters description")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = {
              @Content(
                  array =
                      @ArraySchema(
                          schema = @Schema(implementation = TemplatePageableResponse.class)))
            })
      })
  @ApiErrorResponses
  @PostMapping(
      value = "/v1/list",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  PageableResponse<TemplateResponse> findAll(@Valid @RequestBody TemplatePageableRequest request);
}
