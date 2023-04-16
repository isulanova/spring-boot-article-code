package ru.auchan.backend.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.model.constant.TemplateEnum;

@Getter
@Setter
public class TemplateModel {

  private String stringType;

  private TemplateEnum enumType;

  private UUID idType;

  private LocalDateTime dateType;
}
