package ru.auchan.backend.service;

import org.springframework.transaction.annotation.Transactional;
import ru.auchan.backend.dto.ArticleResponse;
import ru.auchan.backend.dto.ArticleShort;
import ru.auchan.backend.dto.PageableResponse;

public interface ArticleService {
    PageableResponse<ArticleResponse> findAll(
            int page, int size, String sortBy, String sortDirection
    );

    ArticleResponse findByCode(Long code);

    ArticleResponse createArticleFromDictionary(Long code);

    ArticleResponse update(Long code, ArticleShort request);

    @Transactional
    long delete(Long code);
}
