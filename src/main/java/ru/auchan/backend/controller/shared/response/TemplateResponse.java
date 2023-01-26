package ru.auchan.backend.controller.shared.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.model.constant.TemplateEnum;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TemplateResponse {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("stringType")
    private String stringType;

    @JsonProperty("enumType")
    private TemplateEnum enumType;

    @JsonProperty("idType")
    private UUID idType;

    @JsonProperty("dateType")
    private LocalDateTime dateType;
}
