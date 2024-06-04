package ru.auchan.backend.controller.access.metadata;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.auchan.backend.controller.access.shared.request.AccessMapByPermissionGroupRequest;
import ru.auchan.backend.controller.access.shared.request.AccessRequest;
import ru.auchan.backend.controller.access.shared.response.UserAccessMapResponse;

@Tag(
    name = "[ACCESS] UI Access API",
    description =
        "API for getting interface elements " + "that can be displayed within the user role")
@RequestMapping(value = "/api/access")
public interface AccessControllerMetadata {

  @Operation(summary = "Getting a list of user privileges by user ID [Redis]")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserAccessMapResponse.class))
            })
      })
  @GetMapping("/v1/{id}")
  @ResponseStatus(HttpStatus.OK)
  UserAccessMapResponse getAccessMap(@NotNull @PathVariable("id") UUID userId);

  @Operation(summary = "Updating a user's access map")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserAccessMapResponse.class))
            })
      })
  @GetMapping("/v1/renew/{id}")
  @ResponseStatus(value = HttpStatus.OK)
  void renewAccessMap(@NotNull @PathVariable("id") UUID userId);

  @Operation(
      summary = "Getting a list of user privileges by its identifier within a specific module")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserAccessMapResponse.class))
            })
      })
  @PostMapping("/v1/by-group")
  @ResponseStatus(value = HttpStatus.OK)
  UserAccessMapResponse getAccessMapByPermissionGroup(
      @RequestBody AccessMapByPermissionGroupRequest accessRequest);

  @Operation(summary = "Checking if a User has a Privilege")
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
  @PostMapping("/v1/check")
  @ResponseStatus(value = HttpStatus.OK)
  Boolean checkAccess(@RequestBody AccessRequest permissionRequest);
}
