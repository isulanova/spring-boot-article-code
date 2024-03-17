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
import ru.auchan.backend.controller.shared.response.role.RoleGroupItemResponse;

@Tag(name = "[ROLE-GROUP] Role group API", description = "Role Group Management API")
@RequestMapping(value = "/api/role-group")
public interface RoleGroupControllerMetadata {

  @Operation(summary = "Getting a list of role groups available in the system")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = RoleGroupItemResponse.class))
            })
      })
  @GetMapping("/v1")
  ResponseEntity<List<RoleGroupItemResponse>> getRoleGroupList();
}
