package ru.auchan.backend.controller.metadata;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.auchan.backend.controller.shared.request.role.RoleItemRequest;
import ru.auchan.backend.controller.shared.response.role.RoleItemAdminResponse;
import ru.auchan.backend.controller.shared.response.role.RoleItemBySystemNameResponse;
import ru.auchan.backend.controller.shared.response.role.RoleItemResponse;
import ru.auchan.backend.controller.shared.response.role.RoleWithPermissionsItemResponse;
import ru.auchan.backend.controller.shared.response.role.RolesByUserResponse;

@Tag(name = "[ROLE] Role API", description = "Role Management API")
@RequestMapping(value = "/api/role")
public interface RoleControllerMetadata {

  @Operation(summary = "Getting a list of roles available in the system")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = RoleItemAdminResponse.class))
            })
      })
  @GetMapping("/v1")
  ResponseEntity<List<RoleItemAdminResponse>> roleList();

  @Operation(summary = "Getting a list of roles by user's keycloak id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = RolesByUserResponse.class))
            })
      })
  @GetMapping("/v1/user-roles/{id}")
  ResponseEntity<RolesByUserResponse> roleListByKeycloakId(@PathVariable("id") UUID id);

  @Operation(summary = "Getting a role with privileges by role ID")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = RoleWithPermissionsItemResponse.class))
            })
      })
  @GetMapping("/v1/{id}")
  ResponseEntity<RoleWithPermissionsItemResponse> roleWithPermissionsByRoleId(
      @PathVariable("id") UUID id);

  @Operation(summary = "Create a new role")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = RoleItemResponse.class))
            })
      })
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/v1")
  ResponseEntity<RoleItemResponse> createNewRole(@Valid @RequestBody RoleItemRequest itemRequest);

  @Operation(summary = "Remove role")
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
  ResponseEntity<Void> deleteRole(@PathVariable("id") UUID id);

  @Operation(summary = "Change role data")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "202",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = RoleItemResponse.class))
            })
      })
  @ResponseStatus(HttpStatus.ACCEPTED)
  @PutMapping("/v1/{id}")
  ResponseEntity<RoleItemResponse> updateRole(
      @PathVariable("id") UUID id, @Valid @RequestBody RoleItemRequest itemRequest);

  @Operation(summary = "Find by system name")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = RoleItemBySystemNameResponse.class))
            })
      })
  @GetMapping("/v1/by-system-name/{systemName}")
  ResponseEntity<RoleWithPermissionsItemResponse> findBySystemName(
      @PathVariable("systemName") @NotEmpty String systemName);
}
