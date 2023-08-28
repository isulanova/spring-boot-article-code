package ru.auchan.backend.controller.shared.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableRequest {

  @JsonProperty("pageNumber")
  @Min(0)
  @Schema(description = "Номер страницы")
  private int pageNumber;

  @JsonProperty("pageSize")
  @Min(5)
  @Schema(description = "Размер страницы")
  private int pageSize;

  @JsonProperty("sortBy")
  @Schema(description = "Наименование поля сортировки")
  private String sortBy; // default sort value

  @JsonProperty("sortDirection")
  @Schema(description = "Направление сортировки")
  private String sortDirection;

  @Override
  public String toString() {
    return "{"
        + "pageNumber="
        + pageNumber
        + ", pageSize="
        + pageSize
        + ", sortBy='"
        + sortBy
        + '\''
        + ", sortDirection='"
        + sortDirection
        + '\''
        + '}';
  }
}
