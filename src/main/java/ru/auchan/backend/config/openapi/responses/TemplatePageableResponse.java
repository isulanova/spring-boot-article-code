package ru.auchan.backend.config.openapi.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.auchan.backend.controller.shared.response.PageableResponse;
import ru.auchan.backend.controller.shared.response.TemplateResponse;

@Schema(title = "[TEMPLATE] Template pageable response")
public class TemplatePageableResponse extends PageableResponse<TemplateResponse> {}
