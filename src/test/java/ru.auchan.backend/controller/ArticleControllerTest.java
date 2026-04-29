//package ru.auchan.backend.controller;
//
//import ru.auchan.backend.dto.ArticleRequest;
//import ru.auchan.backend.dto.ArticleResponse;
//import ru.auchan.backend.dto.PageableResponse;
//import ru.auchan.backend.service.ArticleService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(MockitoExtension.class)  // ← Используем только Mockito, без Spring
//@DisplayName("Тесты для ArticleController")
//class ArticleControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private ArticleService articleService;
//
//    @InjectMocks
//    private ArticleController articleController;
//
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @BeforeEach
//    void setUp() {
//        // Настраиваем MockMvc с нашим контроллером
//        mockMvc = MockMvcBuilders
//                .standaloneSetup(articleController)  // ← standalone режим, без Spring контекста
//                .build();
//    }
//
//    @Test
//    @DisplayName("POST /api/v1/articles - успешное создание")
//    void createArticle_Success() throws Exception {
//        // given
//        ArticleRequest request = ArticleRequest.builder()
//                .articleCode(1001L)
//                .articleName("iPhone 15")
//                .build();
//
//        ArticleResponse response = ArticleResponse.builder()
//                .id(1L)
//                .articleCode(1001L)
//                .articleName("iPhone 15")
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        when(articleService.createArticle(any(ArticleRequest.class))).thenReturn(response);
//
//        // when & then
//        mockMvc.perform(post("/api/v1/articles")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.articleCode").value(1001L))
//                .andExpect(jsonPath("$.articleName").value("iPhone 15"));
//    }
//
//    @Test
//    @DisplayName("GET /api/v1/articles - получение всех статей с пагинацией")
//    void getAllArticles_Success() throws Exception {
//        // given
//        ArticleResponse article1 = ArticleResponse.builder()
//                .id(1L)
//                .articleCode(1001L)
//                .articleName("iPhone 15")
//                .build();
//
//        ArticleResponse article2 = ArticleResponse.builder()
//                .id(2L)
//                .articleCode(1002L)
//                .articleName("iPhone 14")
//                .build();
//
//        PageableResponse<ArticleResponse> pageableResponse = PageableResponse.<ArticleResponse>builder()
//                .content(List.of(article1, article2))
//                .pageNumber(0)
//                .pageSize(20)
//                .totalElements(2)
//                .totalPages(1)
//                .last(true)
//                .build();
//
//        when(articleService.getAllArticles(0, 20, "id", "ASC")).thenReturn(pageableResponse);
//
//        // when & then
//        mockMvc.perform(get("/api/v1/articles"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content.length()").value(2))
//                .andExpect(jsonPath("$.content[0].articleCode").value(1001L));
//    }
//
//    @Test
//    @DisplayName("GET /api/v1/articles/{code} - успешное получение статьи")
//    void getArticle_Success() throws Exception {
//        // given
//        ArticleResponse response = ArticleResponse.builder()
//                .id(1L)
//                .articleCode(1001L)
//                .articleName("iPhone 15")
//                .build();
//
//        when(articleService.getArticle(1001L)).thenReturn(response);
//
//        // when & then
//        mockMvc.perform(get("/api/v1/articles/1001"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.articleCode").value(1001L));
//    }
//}
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
import ru.auchan.backend.dto.ArticleRequest;
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
    private ArticleRepository articleRepository; // Подставьте ваш репозиторий

    // ⚙️ Секрет и срок действия читаются из application-test.yml
    @Value("yourSecretKeyForJWTTokenGeneration2024LongEnoughForHS256")
//    @Value("${jwt.secret:yourSecretKeyForJWTTokenGeneration2024LongEnoughForHS256}")
    private String jwtSecret;

    @Value("${jwt.expiration:86400000}")
    private long jwtExpirationMs;

    /**
     * Генерирует валидный JWT без ролей.
     * ВАЖНО: Если ваш JwtUtil берёт логин не из поля "sub", а из кастомного claim (например "login"),
     * замените .setSubject(username) на .claim("login", username)
     */
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

    // ==================== Безопасность ====================

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

    // ==================== CRUD ====================

    @Test
    @DisplayName("POST /articles -> 200 при валидном токене")
    void create_ShouldReturnOk() throws Exception {
        ArticleRequest request = ArticleRequest.builder()
                .articleCode(10L)
                .articleName("Тестовое содержимое")
                .build();

        mockMvc.perform(post("/articles")
                        .header("Authorization", authHeader("testuser"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Новая статья"))
                .andExpect(jsonPath("$.content").value("Тестовое содержимое"))
                .andExpect(jsonPath("$.code").isNotEmpty());
    }

    @Test
    @DisplayName("GET /articles -> 200 с пагинацией")
    void getAll_ShouldReturnPaginatedArticles() throws Exception {
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
                .andExpect(jsonPath("$.totalElements").value(3))
                .andExpect(jsonPath("$.totalPages").value(2));
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
                .andExpect(jsonPath("$.code").value(saved.getArticleCode()))
                .andExpect(jsonPath("$.title").value("Single Article"));
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

        ArticleRequest updateReq = ArticleRequest.builder()
                .articleCode(14L)
                .articleName("Updated Content")
                .build();

        mockMvc.perform(put("/articles/{code}", saved.getArticleCode())
                        .header("Authorization", authHeader("testuser"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(saved.getArticleCode()))
                .andExpect(jsonPath("$.title").value("Updated Title"));
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

        assertThat(articleRepository.findByArticleCode(saved.getArticleCode())).isNull();
    }

    @Test
    @DisplayName("POST /articles -> 400 при невалидных данных (валидация)")
    void create_ShouldReturnBadRequest_WhenInvalidData() throws Exception {
        ArticleRequest request = ArticleRequest.builder()
                .articleCode(null)
                .articleName("Content")
                .build();

        mockMvc.perform(post("/articles")
                        .header("Authorization", authHeader("testuser"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}