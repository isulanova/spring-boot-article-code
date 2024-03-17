package ru.auchan.backend.controller.metadata;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.auchan.backend.controller.shared.response.RoleModuleItemResponse;

@Tag(
    name = "[ROLE-MODULE] Role module API",
    description = "API for managing the binding of application modules to user roles")
@RequestMapping(value = "/api/role/module")
public interface RoleModuleControllerMetadata {

  @Operation(summary = "Getting a list of application modules available in the system")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = RoleModuleItemResponse.class))
            })
      })
  @GetMapping("/v1")
  ResponseEntity<List<RoleModuleItemResponse>> getModuleList();
}
