package ru.auchan.backend.controller.shared.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.auchan.backend.model.constant.TemplateEnum;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Набор поисковых фильтров")
public class TemplateRequestSearchFilters {

    @JsonProperty("stringType")
    private String stringType;

    @JsonProperty("enumType")
    private TemplateEnum enumType;

    @JsonProperty("idType")
    private UUID idType;

    @JsonProperty("dateType")
    private LocalDateTime dateType;
}
