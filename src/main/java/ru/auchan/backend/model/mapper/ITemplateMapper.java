package ru.auchan.backend.model.mapper;

import ru.auchan.backend.controller.shared.request.TemplateRequest;
import ru.auchan.backend.controller.shared.response.TemplateResponse;
import ru.auchan.backend.io.entity.TemplateEntity;
import ru.auchan.backend.model.TemplateModel;

import java.util.List;

public interface ITemplateMapper {

    TemplateModel fromEntityToModel(TemplateEntity entity);

    TemplateResponse fromModelToResponse(TemplateModel model);

    TemplateModel fromRequestToModel(TemplateRequest request);

    TemplateEntity fromModelToEntity(TemplateModel model);

    List<TemplateModel> fromListEntityToListModel(List<TemplateEntity> entityList);

    List<TemplateResponse> fromListModelToListResponse(List<TemplateModel> modelList);
}
