package ru.auchan.backend.controller.shared.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.model.constant.TemplateEnum;

@Getter
@Setter
@Schema(title = "[TEMPLATE] Template response")
public class TemplateResponse {

  @Schema(description = "identifier", example = "03782b72-1f02-4c66-9a3d-e63b7b5a1647")
  @JsonProperty("id")
  private UUID id;

  @Schema(description = "String type field", example = "Lorem ipsum dolor sit amet")
  @JsonProperty("stringType")
  private String stringType;

  @Schema(description = "ENUM type field", example = "VALUE1")
  @JsonProperty("enumType")
  private TemplateEnum enumType;

  @Schema(description = "UUID type field", example = "03782b72-1f02-4c66-9a3d-e63b7b5a1647")
  @JsonProperty("idType")
  private UUID idType;

  @Schema(description = "date type field", example = "29.08.1997 10:14:00")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
  @JsonProperty("dateType")
  private LocalDateTime dateType;
}
