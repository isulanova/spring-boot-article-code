package ru.auchan.backend.service.embedded.impl;

import static ru.auchan.backend.io.specification.TemplateEntitySpecification.getSpecification;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.auchan.backend.controller.shared.request.TemplatePageableRequest;
import ru.auchan.backend.controller.shared.request.TemplateRequest;
import ru.auchan.backend.controller.shared.request.TemplateRequestSearchFilters;
import ru.auchan.backend.controller.shared.response.PageableResponse;
import ru.auchan.backend.controller.shared.response.TemplateResponse;
import ru.auchan.backend.io.entity.TemplateEntity;
import ru.auchan.backend.io.repository.TemplateRepo;
import ru.auchan.backend.service.embedded.ITemplateService;
import ru.auchan.backend.transform.ITemplateMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateService implements ITemplateService {

  private static final String RECORD_NOT_FOUND_ERR_MASK = "record with id %s not found";
  private static final String DEFAULT_SORT_BY_FIELD_NAME = "updated";
  private static final String DEFAULT_SORT_DIRECTION = "desc";
  private final TemplateRepo templateRepo;
  private final ITemplateMapper templateMapper;

  @Override
  public TemplateResponse findById(final UUID itemId) {
    final Optional<TemplateEntity> entityOptional = templateRepo.findById(itemId);
    if (entityOptional.isEmpty()) {
      throw new EntityNotFoundException(String.format(RECORD_NOT_FOUND_ERR_MASK, itemId));
    }
    return templateMapper.fromModelToResponse(
        templateMapper.fromEntityToModel(entityOptional.get()));
  }

  @Override
  public void removeById(final UUID itemId) {
    final Optional<TemplateEntity> entityOptional = templateRepo.findById(itemId);
    if (entityOptional.isEmpty()) {
      throw new EntityNotFoundException(String.format(RECORD_NOT_FOUND_ERR_MASK, itemId));
    }
    templateRepo.delete(entityOptional.get());
  }

  @Override
  public TemplateResponse add(final TemplateRequest request) {
    final var persistentEntity =
        templateRepo.save(
            templateMapper.fromModelToEntity(templateMapper.fromRequestToModel(request)));
    return templateMapper.fromModelToResponse(templateMapper.fromEntityToModel(persistentEntity));
  }

  @Override
  public TemplateResponse update(final UUID id, final TemplateRequest request) {
    final Optional<TemplateEntity> entityOptional = templateRepo.findById(id);
    if (entityOptional.isEmpty()) {
      throw new EntityNotFoundException(String.format(RECORD_NOT_FOUND_ERR_MASK, id));
    }

    final var persistentEntity = entityOptional.get();
    persistentEntity.setDateType(request.getDateType());
    persistentEntity.setEnumType(request.getEnumType());
    persistentEntity.setIdType(request.getIdType());
    persistentEntity.setStringType(request.getStringType());

    return templateMapper.fromModelToResponse(templateMapper.fromEntityToModel(persistentEntity));
  }

  @Override
  public PageableResponse<TemplateResponse> findAll(final TemplatePageableRequest request) {
    applyDefaultSort(request);
    try {
      final TemplateRequestSearchFilters filters = request.getFilters();

      final Page<TemplateEntity> reportPage =
          findAll(createSearchSpecification(filters), createPageRequest(request));

      return createResponse(reportPage);
    } catch (final Exception ex) {
      log.error(
          "Error while try find reports  by pageable request: {}.Message: {}",
          request,
          ex.getMessage());
      return new PageableResponse<>();
    }
  }

  private PageableResponse<TemplateResponse> createResponse(final Page<TemplateEntity> page) {
    return new PageableResponse<>(
        page.getNumber(),
        page.getSize(),
        page.getTotalElements(),
        page.getTotalPages(),
        templateMapper.fromListModelToListResponse(
            templateMapper.fromListEntityToListModel(page.getContent())));
  }

  private Specification<TemplateEntity> createSearchSpecification(
          final TemplateRequestSearchFilters filters) {
    return Specification.where(getSpecification(filters));
  }

  private void applyDefaultSort(final TemplatePageableRequest request) {
    if (request.getSortBy() == null || "".equals(request.getSortBy())) {
      request.setSortBy(DEFAULT_SORT_BY_FIELD_NAME);
      request.setSortDirection(DEFAULT_SORT_DIRECTION);
    }
  }

  private Page<TemplateEntity> findAll(
          final Specification<TemplateEntity> specification, final PageRequest pageRequest) {
    return templateRepo.findAll(specification, pageRequest);
  }

  private PageRequest createPageRequest(final TemplatePageableRequest request) {
    return PageRequest.of(
        request.getPageNumber(),
        request.getPageSize(),
        Sort.by(Sort.Direction.fromString(request.getSortDirection()), request.getSortBy()));
  }
}
