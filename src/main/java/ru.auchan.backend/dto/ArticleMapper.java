package ru.auchan.backend.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import ru.auchan.backend.entity.Article;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    ArticleResponse toResponse(Article article);

    List<ArticleResponse> toResponseList(List<Article> articles);

    @Mapping(target = "content", source = "content")
    @Mapping(target = "pageNumber", source = "number")
    @Mapping(target = "pageSize", source = "size")
    @Mapping(target = "totalElements", source = "totalElements")
    @Mapping(target = "totalPages", source = "totalPages")
    @Mapping(target = "last", source = "last")
    PageableResponse<ArticleResponse> toPageableResponse(Page<Article> page);
}