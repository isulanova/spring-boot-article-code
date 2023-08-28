package ru.auchan.backend.controller;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import ru.auchan.backend.controller.metadata.TemplateControllerMetadata;
import ru.auchan.backend.controller.shared.request.TemplatePageableRequest;
import ru.auchan.backend.controller.shared.request.TemplateRequest;
import ru.auchan.backend.controller.shared.response.PageableResponse;
import ru.auchan.backend.controller.shared.response.TemplateResponse;
import ru.auchan.backend.service.embedded.ITemplateService;

@Validated
@RestController
@RequiredArgsConstructor
public class TemplateController implements TemplateControllerMetadata {

  private final ITemplateService templateService;

  @Override
  public ResponseEntity<TemplateResponse> add(final TemplateRequest request) {
    return ResponseEntity.ok(templateService.add(request));
  }

  @Override
  public void removeById(final UUID itemId) {
    templateService.removeById(itemId);
  }

  @Override
  public ResponseEntity<TemplateResponse> update(final UUID id, final TemplateRequest request) {
    return ResponseEntity.ok(templateService.update(id, request));
  }

  @Override
  public ResponseEntity<TemplateResponse> findById(final UUID itemId) {
    return ResponseEntity.ok(templateService.findById(itemId));
  }

  @Override
  public PageableResponse<TemplateResponse> findAll(final TemplatePageableRequest request) {
    return templateService.findAll(request);
  }
}
