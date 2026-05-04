package ru.auchan.backend.service;

import lombok.RequiredArgsConstructor;
import ru.auchan.backend.exception.ResourceNotFoundException;
import ru.auchan.backend.exception.ResourceAlreadyExistsException;
import ru.auchan.backend.dto.ArticleRequest;
import ru.auchan.backend.dto.ArticleResponse;
import ru.auchan.backend.dto.PageableResponse;
import ru.auchan.backend.entity.Article;
import ru.auchan.backend.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    private ArticleResponse mapToResponse(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .articleCode(article.getArticleCode())
                .articleName(article.getArticleName())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .build();
    }

    public PageableResponse<ArticleResponse> findAll(
            int page, int size, String sortBy, String sortDirection
    ) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("DESC")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page <Article> articlePage = articleRepository.findAll(pageable);
        return PageableResponse.<ArticleResponse>builder()
                .content(articlePage.getContent().stream().map(this::mapToResponse).toList())
                .pageNumber(articlePage.getNumber())
                .pageSize(articlePage.getSize())
                .totalElements(articlePage.getTotalElements())
                .totalPages(articlePage.getTotalPages())
                .last(articlePage.isLast())
                .build();
    }

    public Article findByCode(Long code) {
        return articleRepository.findByArticleCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                "Article",
                "articleCode",
                        code
        ));
    }

    public Article create(ArticleRequest request) {
        if (articleRepository.existsByArticleCode(request.getArticleCode())) {
            throw new ResourceAlreadyExistsException(
                    "Article",
                    "articleCode",
                    request.getArticleCode()
            );
        }

        Article article = Article.builder()
                .articleCode(request.getArticleCode())
                .articleName(request.getArticleName())
                .build();
        return articleRepository.save(article);
    }

    public Article update(Long code, ArticleRequest request) {
        Article article = findByCode(code);
        article.setArticleName(request.getArticleName());
        return articleRepository.save(article);
    }

    @Transactional
    public long delete(Long code) {
        long deletedCount =  articleRepository.deleteByArticleCode(code);
        if (deletedCount == 0) {
            throw new ResourceNotFoundException(
                    "Article",
                    "articleCode",
                    code
            );
        }
        return deletedCount;
    }
}
