package ru.auchan.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import ru.auchan.backend.dto.ArticleCreateRequest;
import ru.auchan.backend.dto.ArticleShort;
import ru.auchan.backend.dto.ArticleResponse;
import ru.auchan.backend.dto.PageableResponse;
import ru.auchan.backend.service.ArticleService;
import ru.auchan.backend.service.impl.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/backend/articles")
@Tag(name = "Управление артикулами", description = "API для создания, получения, обновления и удаления артикулов")
public class ArticleController {

    private final ArticleService articleService;

    @Operation(summary = "Список артикулов с пагинацией")
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

    @Operation(summary = "Получить артикул по articleCode", description = "Возвращает полную информацию об артикуле")
    @GetMapping("/{code}")
    public ArticleResponse getByCode(@PathVariable Long code) {
        return articleService.findByCode(code);
    }

    @Operation(summary = "Создать запись об артикуле", description = "Проверяет наличие артикула в словаре и записывает в бд")
    @PostMapping
    public ArticleResponse create(@Validated @RequestBody ArticleCreateRequest request) {
        return articleService.createArticleFromDictionary(request.getArticleCode());
    }

    @Operation(summary = "Обновить артикул по articleCode")
    @PutMapping("/{code}")
    public ArticleResponse update(@PathVariable Long code, @RequestBody ArticleShort request) {
        return articleService.update(code, request);
    }

    @Operation(summary = "Удалить артикул по articleCode")
    @DeleteMapping("/{code}")
    public long delete(@PathVariable Long code) {
        return articleService.delete(code);
    }
}
