package ru.auchan.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequest {

    @NotNull(message = "Article code is required")
    @Min(value = 1, message = "Article code must be positive")
    private Long articleCode;

    @NotBlank(message = "Article name is required")
    private String articleName;
}

