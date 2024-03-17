package ru.auchan.backend.controller.metadata;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.auchan.backend.controller.shared.request.view.RoleModelUpdatePermissionRequest;
import ru.auchan.backend.controller.shared.response.role.model.system.RoleModelSystem;
import ru.auchan.backend.controller.shared.response.role.model.view.RoleModelResponse;

@Tag(name = "[ROLE-MODEL] Role model API", description = "Role Model Management API")
@RequestMapping(value = "/api/role-model")
public interface RoleModelControllerMetadata {

  @Operation(summary = "Update/Add Role Privilege Binding")
  @ApiResponses(value = {@ApiResponse(responseCode = "201")})
  @PutMapping(value = "/v1")
  ResponseEntity<Void> updatePermission(
      @Valid @RequestBody RoleModelUpdatePermissionRequest roleModelRequest);

  @Operation(summary = "Get an up-to-date role model")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = RoleModelResponse.class))
            })
      })
  @GetMapping(value = "/v1")
  ResponseEntity<RoleModelResponse> getRoleModel();

  @Operation(
      summary =
          "Getting the actual role model in the format used in the "
              + "method of restoring a role model from its state snapshot")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = RoleModelSystem.class))
            })
      })
  @GetMapping(value = "/v1/system")
  ResponseEntity<RoleModelSystem> getRoleModelSystem();

  @Operation(summary = "Restoring a role model from a state nugget")
  @ApiResponses(value = {@ApiResponse(responseCode = "201")})
  @PostMapping(value = "/v1/apply")
  ResponseEntity<Void> applyRoleModel(@RequestBody RoleModelSystem roleModelSystem);
}
