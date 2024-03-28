package ru.auchan.backend.controller.user.metadata;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
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
import ru.auchan.backend.controller.util.ListWrapper;
import ru.auchan.backend.controller.user.shared.request.AuthUserUpdateRequest;
import ru.auchan.backend.controller.user.shared.request.UserCreateRequest;
import ru.auchan.backend.controller.user.shared.response.AuthUserItemWithRolesResponse;
import ru.auchan.backend.controller.user.shared.response.UpdatedUserRoleServiceResponse;

@Tag(name = "[USER] User API", description = "User Management API")
@RequestMapping(value = "/api/user")
public interface UserRestControllerMetadata {

  @Operation(summary = "Getting a list of users by a list of role IDs")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UUID.class))
            })
      })
  @PostMapping("/v1/by-role-ids")
  ResponseEntity<Set<UUID>> findUsersByRoleIds(@RequestBody List<UUID> roleIdList);

  @Operation(summary = "Getting a list of users by the system name of the role")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    array = @ArraySchema(schema = @Schema(implementation = UUID.class)),
                    mediaType = "application/json"))
      })
  @PostMapping("/v1/by-role-names")
  ResponseEntity<Set<UUID>> findUsersByRoleSystemNames(@RequestBody List<String> roleNameList);

  @Operation(summary = "Search user by ID keycloak")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = AuthUserItemWithRolesResponse.class))
            })
      })
  @GetMapping(value = "/v1/{keycloak_id}", produces = "application/json")
  ResponseEntity<AuthUserItemWithRolesResponse> findByKeycloakId(
      @PathVariable("keycloak_id") UUID id);

  @Operation(summary = "List of users with roles by keycloak ID list")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    array =
                        @ArraySchema(
                            schema =
                                @Schema(implementation = AuthUserItemWithRolesResponse.class))))
      })
  @PostMapping("/v1/keycloak/list")
  ResponseEntity<List<AuthUserItemWithRolesResponse>> findByKeycloakIdList(
      @RequestBody ListWrapper<UUID> uuidList);

  @Operation(summary = "Adding a user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = AuthUserItemWithRolesResponse.class))
            })
      })
  @PostMapping("/v1")
  ResponseEntity<AuthUserItemWithRolesResponse> addUser(
      @Valid @RequestBody UserCreateRequest userCreateRequest);



  @Operation(summary = "User update")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "202",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UpdatedUserRoleServiceResponse.class))
            })
      })
  @PutMapping("/v1/{keycloak_id}")
  ResponseEntity<UpdatedUserRoleServiceResponse> update(
      @PathVariable("keycloak_id") UUID id,
      @Valid @RequestBody AuthUserUpdateRequest updateRequest);

  @Operation(summary = "Search users by role ID")
  @GetMapping("/v1/list/{role_id}")
  ResponseEntity<Map<String, Object>> findByRole(
      @PathVariable("role_id") UUID id,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size);

  @Operation(summary = "User search by username")
  @GetMapping("/v1/find/{username}")
  ResponseEntity<Map<String, Object>> findByUserName(
      @PathVariable("username") String username,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size);

  @Operation(summary = "Delete user by ID keycloak")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Boolean.class))
            })
      })
  @DeleteMapping("/v1/{keycloak_id}")
  ResponseEntity<Boolean> deleteByKeycloakId(@PathVariable("keycloak_id") UUID id);

  @Operation(summary = "Add role to user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Boolean.class))
            })
      })
  @PostMapping("/v1/{userKeycloakId}/role/{roleSystemName}")
  ResponseEntity<Boolean> addRoleToUser(
      @PathVariable("userKeycloakId") @NotNull UUID userKeycloakId,
      @PathVariable("roleSystemName") @NotBlank String roleSystemName);

  @Operation(summary = "Remove role from user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Boolean.class))
            })
      })
  @DeleteMapping("/v1/{userKeycloakId}/role/{roleSystemName}")
  ResponseEntity<Boolean> removeRoleFromUser(
      @PathVariable("userKeycloakId") @NotNull UUID userKeycloakId,
      @PathVariable("roleSystemName") @NotBlank String roleSystemName);
}
