package ru.auchan.backend.model;

import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.model.constant.TemplateEnum;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TemplateModel {

    private String stringType;

    private TemplateEnum enumType;

    private UUID idType;

    private LocalDateTime dateType;
}
