package ru.auchan.backend.controller.permission.metadata;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.auchan.backend.controller.permission.shared.request.PermissionGroupRequest;
import ru.auchan.backend.controller.permission.shared.response.PermissionGroupResponse;

@Tag(
    name = "[PERMISSION-GROUP] Permission group API",
    description = "Privilege Group Management API")
@RequestMapping(value = "/api/permission-groups")
public interface PermissionGroupControllerMetadata {

  @Operation(summary = "Getting a list of privilege groups available in the system")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PermissionGroupResponse.class))
            })
      })
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/v1")
  ResponseEntity<List<PermissionGroupResponse>> getPermissionGroups();

  @Operation(summary = "Getting a group of privileges by ID")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PermissionGroupResponse.class))
            })
      })
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/v1/{id}")
  ResponseEntity<PermissionGroupResponse> getById(@PathVariable("id") UUID id);

  @Operation(summary = "Creating a New Privilege Group")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PermissionGroupResponse.class))
            })
      })
  @PostMapping("/v1")
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<PermissionGroupResponse> createNewPermissionGroup(
      @Valid @RequestBody PermissionGroupRequest itemRequest);

  @Operation(summary = "Deleting a privilege group")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class))
            })
      })
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/v1/{id}")
  ResponseEntity<Void> delete(@PathVariable("id") UUID id);
}
