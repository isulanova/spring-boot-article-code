package ru.auchan.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.auchan.backend.client.DictionaryClient;
import ru.auchan.backend.dto.*;
import ru.auchan.backend.entity.Article;
import ru.auchan.backend.repository.ArticleRepository;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@DisplayName("Интеграционные тесты ArticleController (JWT без ролей)")
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ArticleRepository articleRepository;

    @MockitoBean
    private ArticleMapper articleMapper;

    @MockitoBean
    private DictionaryClient dictionaryClient;

    @Value("yourSecretKeyForJWTTokenGeneration2024LongEnoughForHS256")
    private String jwtSecret;

    @Value("${jwt.expiration:86400000}")
    private long jwtExpirationMs;

    private String generateToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    private String authHeader(String username) {
        return "Bearer " + generateToken(username);
    }

    @Test
    @DisplayName("GET /api/backend/articles -> 401 при отсутствии токена")
    void getAll_ShouldReturn401_WhenNoToken() throws Exception {
        mockMvc.perform(get("/api/backend/articles"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/backend/articles -> 401 при невалидном токене")
    void getAll_ShouldReturn401_WhenInvalidToken() throws Exception {
        mockMvc.perform(get("/api/backend/articles")
                        .header("Authorization", "Bearer broken.token.here"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("POST /api/backend/articles -> 200 при валидном токене")
    void create_ShouldReturnOk() throws Exception {
        Long articleCode = 10L;

        ArticleShort request = ArticleShort.builder()
                .articleCode(articleCode)
                .articleName("Тестовое содержимое")
                .build();

        ArticleDictionaryResponse dictionaryResponse = ArticleDictionaryResponse.builder()
                .code(articleCode)
                .name("Тестовое содержимое")
                .build();

        when(dictionaryClient.getArticleByCode(articleCode))
                .thenReturn(ResponseEntity.ok(dictionaryResponse));

        ArticleResponse response = ArticleResponse.builder()
                .id(1L)
                .articleCode(articleCode)
                .articleName("Тестовое содержимое")
                .build();

        when(articleMapper.toResponse(any(Article.class))).thenReturn(response);

        mockMvc.perform(post("/api/backend/articles")
                        .header("Authorization", authHeader("testuser"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articleName").value("Тестовое содержимое"))
                .andExpect(jsonPath("$.articleCode").value(articleCode));
        assertThat(articleRepository.existsByArticleCode(articleCode)).isTrue();
    }

    @Test
    @DisplayName("GET /api/backend/articles -> 200 с пагинацией")
    void getAll_ShouldReturnPaginatedArticles() throws Exception {
//        long existingCount = articleRepository.count();
        List<Article> savedArticles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Article saved = articleRepository.save(Article.builder()
                    .articleCode(11L + i)
                    .articleName("Content " + i)
                    .build());
            savedArticles.add(saved);
        }

        List<ArticleResponse> expectedContent = savedArticles.stream()
                .limit(2)
                .map(article -> ArticleResponse.builder()
                        .id(article.getId())
                        .articleCode(article.getArticleCode())
                        .articleName(article.getArticleName())
                        .createdAt(article.getCreatedAt())
                        .updatedAt(article.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        PageableResponse<ArticleResponse> expectedResponse = PageableResponse.<ArticleResponse>builder()
                .content(expectedContent)
                .pageNumber(0)
                .pageSize(2)
                .totalElements(3L)
                .totalPages(2)
                .last(false)
                .build();

        Page<Article> articlePage = new PageImpl<>(savedArticles, PageRequest.of(0, 2), 3);
        when(articleMapper.toPageableResponse(any(Page.class))).thenReturn(expectedResponse);

        mockMvc.perform(get("/api/backend/articles")
                        .header("Authorization", authHeader("testuser"))
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.totalElements").value(3))
                .andExpect(jsonPath("$.totalPages").value(2));
    }

    @Test
    @DisplayName("GET /api/backend/articles/{code} -> 200 если статья существует")
    void getByCode_ShouldReturnArticle() throws Exception {
        Article saved = articleRepository.save(Article.builder()
                .articleCode(12L)
                .articleName("Single Content")
                .build());

        ArticleResponse expectedResponse = ArticleResponse.builder()
                .id(saved.getId())
                .articleCode(12L)
                .articleName("Single Content")
                .createdAt(saved.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        when(articleMapper.toResponse(any(Article.class))).thenReturn(expectedResponse);

        mockMvc.perform(get("/api/backend/articles/{code}", saved.getArticleCode())
                        .header("Authorization", authHeader("testuser")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articleCode").value(saved.getArticleCode()))
                .andExpect(jsonPath("$.articleName").value("Single Content"));
    }

    @Test
    @DisplayName("GET /api/backend/articles/{code} -> 404 если статьи нет")
    void getByCode_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/backend/articles/{code}", 999999)
                        .header("Authorization", authHeader("testuser")))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/backend/articles/{code} -> 200 при обновлении")
    void update_ShouldReturnUpdatedArticle() throws Exception {
        Article saved = articleRepository.save(Article.builder()
                .articleCode(13L)
                .articleName("Old Content")
                .build());

        ArticleShort updateReq = ArticleShort.builder()
                .articleCode(13L)
                .articleName("Updated Content")
                .build();

        ArticleResponse expectedResponse = ArticleResponse.builder()
                .id(saved.getId())
                .articleCode(13L)
                .articleName("Updated Content")
                .createdAt(saved.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        when(articleMapper.toResponse(any(Article.class))).thenReturn(expectedResponse);

        mockMvc.perform(put("/api/backend/articles/{code}", saved.getArticleCode())
                        .header("Authorization", authHeader("testuser"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articleCode").value(saved.getArticleCode()))
                .andExpect(jsonPath("$.articleName").value("Updated Content"));
    }

    @Test
    @DisplayName("DELETE /api/backend/articles/{code} -> 200 и фактическое удаление")
    void delete_ShouldReturnDeletedCount() throws Exception {
        Article saved = articleRepository.save(Article.builder()
                .articleCode(15L)
                .articleName("Will be deleted")
                .build());

        mockMvc.perform(delete("/api/backend/articles/{code}", saved.getArticleCode())
                        .header("Authorization", authHeader("testuser")))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        assertThat(articleRepository.findByArticleCode(saved.getArticleCode())).isEmpty();
    }
}