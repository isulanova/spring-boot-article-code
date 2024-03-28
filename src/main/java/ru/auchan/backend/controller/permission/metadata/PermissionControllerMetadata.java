package ru.auchan.backend.controller.permission.metadata;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.auchan.backend.controller.permission.shared.request.PermissionItemRequest;
import ru.auchan.backend.controller.permission.shared.response.PermissionItemResponse;
import ru.auchan.backend.controller.util.ListWrapper;

@Tag(name = "[PERMISSION] Permission API", description = "Privilege Management API")
@RequestMapping(value = "/api/permissions")
public interface PermissionControllerMetadata {

  @Operation(summary = "Getting a list of privileges available in the system [Pagination]")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PermissionItemResponse.class))
            })
      })
  @GetMapping("/v1")
  ResponseEntity<Map<String, Object>> getPermissionListPageable(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size);

  @Operation(summary = "Getting a list of privileges available in the system")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PermissionItemResponse.class))
            })
      })
  @GetMapping("/v1/all")
  ResponseEntity<Set<PermissionItemResponse>> getPermissionList();

  @Operation(
      summary =
          "Getting a list of privileges available in the system by the list of privilege groups")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PermissionItemResponse.class))
            })
      })
  @PostMapping("/v1/by-permission-group")
  ResponseEntity<Set<PermissionItemResponse>> getPermissionListByPermissionGroupList(
      @RequestBody ListWrapper<String> groupList);

  @Operation(summary = "Getting a privilege by ID")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PermissionItemResponse.class))
            })
      })
  @GetMapping("/v1/{id}")
  ResponseEntity<PermissionItemResponse> getById(@PathVariable("id") UUID id);

  @Operation(summary = "Creating a new permission")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PermissionItemResponse.class))
            })
      })
  @PostMapping("/v1")
  ResponseEntity<PermissionItemResponse> create(
      @Valid @RequestBody PermissionItemRequest itemRequest);

  @Operation(summary = "Removing a permission")
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
  @DeleteMapping("/v1/{id}")
  ResponseEntity<Void> deletePermission(@PathVariable("id") UUID id);

  @Operation(summary = "Change permission data")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "202",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PermissionItemResponse.class))
            })
      })
  @PutMapping("/v1/{id}")
  ResponseEntity<PermissionItemResponse> update(
      @PathVariable("id") UUID id, @Valid @RequestBody PermissionItemRequest itemRequest);
}
