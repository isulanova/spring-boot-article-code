package ru.auchan.backend.client;

import ru.auchan.backend.dto.ArticleDictionaryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "dictionary",
        url = "http://localhost:8081"
)
public interface DictionaryClient {

    @GetMapping("/api/dictionary/articles/{code}")
    ResponseEntity<ArticleDictionaryResponse> getArticleByCode(@PathVariable("code") Long code);
}