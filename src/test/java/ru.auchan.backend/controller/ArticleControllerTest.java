package ru.auchan.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.auchan.backend.dto.ArticleShort;
import ru.auchan.backend.entity.Article;
import ru.auchan.backend.repository.ArticleRepository;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
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
    @DisplayName("GET /articles -> 401 при отсутствии токена")
    void getAll_ShouldReturn401_WhenNoToken() throws Exception {
        mockMvc.perform(get("/articles"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /articles -> 401 при невалидном токене")
    void getAll_ShouldReturn401_WhenInvalidToken() throws Exception {
        mockMvc.perform(get("/articles")
                        .header("Authorization", "Bearer broken.token.here"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("POST /articles -> 200 при валидном токене")
    void create_ShouldReturnOk() throws Exception {
        ArticleShort request = ArticleShort.builder()
                .articleCode(10L)
                .articleName("Тестовое содержимое")
                .build();

        mockMvc.perform(post("/articles")
                        .header("Authorization", authHeader("testuser"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articleName").value("Тестовое содержимое"))
                .andExpect(jsonPath("$.articleCode").isNotEmpty());
    }

    @Test
    @DisplayName("GET /articles -> 200 с пагинацией")
    void getAll_ShouldReturnPaginatedArticles() throws Exception {
        long existingCount = articleRepository.count();
        for (int i = 0; i < 3; i++) {
            articleRepository.save(Article.builder()
                    .articleCode(11L + i)
                    .articleName("Content " + i)
                    .build());
        }

        mockMvc.perform(get("/articles")
                        .header("Authorization", authHeader("testuser"))
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.totalElements").value(3 + existingCount))
                .andExpect(jsonPath("$.totalPages").value(2 + existingCount/2));
    }

    @Test
    @DisplayName("GET /articles/{code} -> 200 если статья существует")
    void getByCode_ShouldReturnArticle() throws Exception {
        Article saved = articleRepository.save(Article.builder()
                .articleCode(12L)
                .articleName("Single Content")
                .build());

        mockMvc.perform(get("/articles/{code}", saved.getArticleCode())
                        .header("Authorization", authHeader("testuser")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articleCode").value(saved.getArticleCode()))
                .andExpect(jsonPath("$.articleName").value("Single Content"));
    }

    @Test
    @DisplayName("GET /articles/{code} -> 404 если статьи нет")
    void getByCode_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/articles/{code}", 999999)
                        .header("Authorization", authHeader("testuser")))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /articles/{code} -> 200 при обновлении")
    void update_ShouldReturnUpdatedArticle() throws Exception {
        Article saved = articleRepository.save(Article.builder()
                .articleCode(13L)
                .articleName("Old Content")
                .build());

        ArticleShort updateReq = ArticleShort.builder()
                .articleCode(14L)
                .articleName("Updated Content")
                .build();

        mockMvc.perform(put("/articles/{code}", saved.getArticleCode())
                        .header("Authorization", authHeader("testuser"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articleCode").value(saved.getArticleCode()))
                .andExpect(jsonPath("$.articleName").value("Updated Content"));
    }

    @Test
    @DisplayName("DELETE /articles/{code} -> 200 и фактическое удаление")
    void delete_ShouldReturnDeletedCount() throws Exception {
        Article saved = articleRepository.save(Article.builder()
                .articleCode(15L)
                .articleName("Will be deleted")
                .build());

        mockMvc.perform(delete("/articles/{code}", saved.getArticleCode())
                        .header("Authorization", authHeader("testuser")))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        assertThat(articleRepository.findByArticleCode(saved.getArticleCode())).isEmpty();
    }
}