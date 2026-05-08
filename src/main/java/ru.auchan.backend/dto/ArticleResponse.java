package ru.auchan.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {
    @Schema(description = "ID артикула", example = "1")
    private Long id;

    @Schema(description = "Код артикула", example = "123")
    private Long articleCode;

    @Schema(description = "Название артикула", example = "Масло")
    private String articleName;

    @Schema(description = "Дата создания", example = "2026-04-27 14:19:02.989695")
    private LocalDateTime createdAt;

    @Schema(description = "Дата обновления", example = "2026-04-27 14:19:02.989695")
    private LocalDateTime updatedAt;
}