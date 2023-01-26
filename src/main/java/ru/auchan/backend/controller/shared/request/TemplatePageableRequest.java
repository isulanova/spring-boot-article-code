package ru.auchan.backend.controller.shared.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Поисковый запрос с пагинацией")
public class TemplatePageableRequest extends PageableRequest {

    @JsonProperty("filters")
    @Schema(description = "Набор поисковых фильтров")
    private TemplateRequestSearchFilters filters;

    @Override
    public String toString() {
        return "{" + "filters=" + filters + '}';
    }
}
