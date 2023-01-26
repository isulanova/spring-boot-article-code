package ru.auchan.backend.service.embedded;

import ru.auchan.backend.controller.shared.request.TemplatePageableRequest;
import ru.auchan.backend.controller.shared.request.TemplateRequest;
import ru.auchan.backend.controller.shared.response.PageableResponse;
import ru.auchan.backend.controller.shared.response.TemplateResponse;

import java.util.UUID;

public interface ITemplateService {

    TemplateResponse findById(UUID itemId);

    void removeById(UUID itemId);

    TemplateResponse add(TemplateRequest request);

    TemplateResponse update(UUID id, TemplateRequest request);

    PageableResponse<TemplateResponse> findAll(TemplatePageableRequest request);
}
