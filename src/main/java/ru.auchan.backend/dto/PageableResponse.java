package ru.auchan.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageableResponse<T> {
    @Schema(description = "Список артикулов")
    private List<T> content;

    @Schema(description = "Номер страницы", example = "0")
    private int pageNumber;

    @Schema(description = "Количество артикулов на странице", example = "2")
    private int pageSize;

    @Schema(description = "Общее количество элементов", example = "123")
    private long totalElements;

    @Schema(description = "Общее количество страниц", example = "5")
    private int totalPages;

    @Schema(description = "Является ли страница последней", example = "true")
    private boolean last;
}