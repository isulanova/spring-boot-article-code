package ru.auchan.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.auchan.backend.client.DictionaryClient;
import ru.auchan.backend.dto.*;
import ru.auchan.backend.exception.DictionaryNotFoundException;
import ru.auchan.backend.exception.ResourceNotFoundException;
import ru.auchan.backend.entity.Article;
import ru.auchan.backend.repository.ArticleRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.auchan.backend.service.ArticleService;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final DictionaryClient dictionaryClient;
    private final ArticleMapper articleMapper;

    public PageableResponse<ArticleResponse> findAll(
            int page, int size, String sortBy, String sortDirection
    ) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("DESC")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page <Article> articlePage = articleRepository.findAll(pageable);
        return articleMapper.toPageableResponse(articlePage);
    }

    public ArticleResponse findByCode(Long code) {
        Article article = articleRepository.findByArticleCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                "Article",
                "articleCode",
                        code
        ));
        return articleMapper.toResponse(article);
    }


//    public Article create(ArticleRequest request) {
//        if (articleRepository.existsByArticleCode(request.getArticleCode())) {
//            throw new ResourceAlreadyExistsException(
//                    "Article",
//                    "articleCode",
//                    request.getArticleCode()
//            );
//        }
//
//        Article article = Article.builder()
//                .articleCode(request.getArticleCode())
//                .articleName(request.getArticleName())
//                .build();
//        return articleRepository.save(article);
//    }

    public ArticleResponse createArticleFromDictionary(Long code) {
        try {
            ResponseEntity<ArticleDictionaryResponse> response = dictionaryClient.getArticleByCode(code);

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                throw new ResourceNotFoundException(
                        "Article",
                        "articleCode",
                        code
                );
            }

            ArticleDictionaryResponse dictArticle = response.getBody();

            Article article = Article.builder()
                    .articleCode(dictArticle.getCode())
                    .articleName(dictArticle.getName())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            Article saved = articleRepository.save(article);
            return articleMapper.toResponse(saved);

        } catch (feign.FeignException.NotFound e) {
            throw new DictionaryNotFoundException("Article", "articleCode", code);
        } catch (feign.FeignException e) {
            throw new RuntimeException("Dictionary service unavailable: " + e.getMessage(), e);
        }
    }

    public ArticleResponse update(Long code, ArticleShort request) {
        Article article = articleRepository.findByArticleCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Article",
                        "articleCode",
                        code
                ));
        article.setArticleName(request.getArticleName());
        Article res = articleRepository.save(article);
        return articleMapper.toResponse(res);
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
