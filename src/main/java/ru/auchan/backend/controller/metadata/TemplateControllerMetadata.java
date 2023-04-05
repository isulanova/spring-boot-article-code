package ru.auchan.backend.controller.metadata;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.auchan.backend.controller.shared.request.TemplatePageableRequest;
import ru.auchan.backend.controller.shared.request.TemplateRequest;
import ru.auchan.backend.controller.shared.response.PageableResponse;
import ru.auchan.backend.controller.shared.response.TemplateResponse;
import ru.auchan.backend.config.exception.ApiError;

import javax.validation.Valid;
import java.util.UUID;

@Tag(
        name = "Template API",
        description = "API Description")
@RequestMapping("/template")
public interface TemplateControllerMetadata {

    @Operation(summary = "Add item description")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Запись создана",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = TemplateResponse.class))
                            }
                    ),
                    @ApiResponse(responseCode = "400", description = "Неверный аргумент"),
                    @ApiResponse(responseCode = "401", description = "Требуется авторизация"),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка сервера",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class))
                            }
                    )
            })
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping
    ResponseEntity<TemplateResponse> add(@Valid @RequestBody TemplateRequest request);

    @Operation(summary = "Delete item description")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Запись удалена"),
                    @ApiResponse(responseCode = "400", description = "Неверный аргумент"),
                    @ApiResponse(responseCode = "401", description = "Требуется авторизация"),
                    @ApiResponse(responseCode = "404", description = "Запись не найдена"),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка сервера",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class))
                            }
                    )
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{itemId}")
    void removeById(@PathVariable("itemId") UUID itemId);

    @Operation(summary = "Update item description")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Изменения приняты",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = TemplateResponse.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Неверный аргумент"),
                    @ApiResponse(responseCode = "401", description = "Требуется авторизация"),
                    @ApiResponse(responseCode = "404", description = "Запись не найдена"),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка сервера",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class))
                            }
                    )
            })
    @PutMapping("/{itemId}")
    ResponseEntity<TemplateResponse> update(
            @PathVariable("itemId") UUID id,
            @Valid @RequestBody TemplateRequest request);


    @Operation(summary = "Find by id description")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запись найдена",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = TemplateResponse.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Неверный аргумент"),
                    @ApiResponse(responseCode = "401", description = "Требуется авторизация"),
                    @ApiResponse(responseCode = "404", description = "Запись не найдена"),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка сервера",
                            content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))
                            }
                    )
            })
    @GetMapping("/{itemId}")
    ResponseEntity<TemplateResponse> findById(@PathVariable("itemId") UUID itemId);

    @Operation( summary = "Find with pageable and filters description")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Список сформирован", content = {
                            @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = TemplateResponse.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "Неверный аргумент"),
                    @ApiResponse(responseCode = "401", description = "Требуется авторизация"),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка сервера",
                            content = {
                                    @Content (
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )
                            }
                        )
                    }
                )
    @PostMapping("/list")
    PageableResponse<TemplateResponse> findAll(@Valid @RequestBody TemplatePageableRequest request);
}
