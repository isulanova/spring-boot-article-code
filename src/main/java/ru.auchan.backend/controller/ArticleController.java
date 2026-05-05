package ru.auchan.backend.controller;

import org.springframework.validation.annotation.Validated;
import ru.auchan.backend.dto.ArticleCreateRequest;
import ru.auchan.backend.dto.ArticleShort;
import ru.auchan.backend.dto.ArticleResponse;
import ru.auchan.backend.dto.PageableResponse;
import ru.auchan.backend.entity.Article;
import ru.auchan.backend.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/backend/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public ResponseEntity<PageableResponse<ArticleResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        PageableResponse<ArticleResponse> response = articleService.findAll(page, size, sortBy, sortDirection);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{code}")
    public Article getByCode(@PathVariable Long code) {
        return articleService.findByCode(code);
    }

    @PostMapping
    public ArticleResponse create(@Validated @RequestBody ArticleCreateRequest request) {
        return articleService.createArticleFromDictionary(request.getArticleCode());
    }

    @PutMapping("/{code}")
    public Article update(@PathVariable Long code, @RequestBody ArticleShort request) {
        return articleService.update(code, request);
    }

    @DeleteMapping("/{code}")
    public long delete(@PathVariable Long code) {
        return articleService.delete(code);
    }
}
