package ru.auchan.backend.transform;

import java.util.List;
import ru.auchan.backend.controller.shared.request.TemplateRequest;
import ru.auchan.backend.controller.shared.response.TemplateResponse;
import ru.auchan.backend.io.entity.TemplateEntity;
import ru.auchan.backend.model.TemplateModel;

public interface ITemplateMapper {

  TemplateModel fromEntityToModel(TemplateEntity entity);

  TemplateResponse fromModelToResponse(TemplateModel model);

  TemplateModel fromRequestToModel(TemplateRequest request);

  TemplateEntity fromModelToEntity(TemplateModel model);

  List<TemplateModel> fromListEntityToListModel(List<TemplateEntity> entityList);

  List<TemplateResponse> fromListModelToListResponse(List<TemplateModel> modelList);
}
