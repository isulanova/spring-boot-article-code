package ru.auchan.backend.controller.shared.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.auchan.backend.model.constant.TemplateEnum;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateRequest {

    @JsonProperty("stringType")
    private String stringType;

    @JsonProperty("enumType")
    private TemplateEnum enumType;

    @JsonProperty("idType")
    private UUID idType;

    @JsonProperty("dateType")
    private LocalDateTime dateType;
}
