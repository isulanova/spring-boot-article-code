package ru.auchan.backend.model.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import ru.auchan.backend.controller.shared.request.TemplateRequest;
import ru.auchan.backend.controller.shared.response.TemplateResponse;
import ru.auchan.backend.io.entity.TemplateEntity;
import ru.auchan.backend.model.TemplateModel;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TemplateMapper implements ITemplateMapper {

    private final ModelMapper modelMapper;

    public TemplateModel fromEntityToModel(TemplateEntity entity) {
        return modelMapper.map(entity, TemplateModel.class);
    }

    public TemplateEntity fromModelToEntity(TemplateModel model) {
        return modelMapper.map(model, TemplateEntity.class);
    }

    public TemplateResponse fromModelToResponse(TemplateModel model) {
        return modelMapper.map(model, TemplateResponse.class);
    }

    public TemplateModel fromRequestToModel(TemplateRequest request) {
        return modelMapper.map(request, TemplateModel.class);
    }

    public List<TemplateModel> fromListEntityToListModel(List<TemplateEntity> entityList) {
        return modelMapper.map(entityList, new TypeToken<List<TemplateModel>>() {}.getType());
    }

    public List<TemplateResponse> fromListModelToListResponse(List<TemplateModel> modelList) {
        return modelMapper.map(modelList, new TypeToken<List<TemplateResponse>>() {}.getType());
    }
}
