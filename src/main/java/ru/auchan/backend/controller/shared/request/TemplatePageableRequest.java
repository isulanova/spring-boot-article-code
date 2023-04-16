package ru.auchan.backend.controller.shared.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "[TEMPLATE] Template pageable request")
public class TemplatePageableRequest extends PageableRequest {

  @Schema(description = "Filters")
  @JsonProperty("filters")
  private TemplateRequestSearchFilters filters;

  @Override
  public String toString() {
    return "{" + "filters=" + filters + '}';
  }
}
