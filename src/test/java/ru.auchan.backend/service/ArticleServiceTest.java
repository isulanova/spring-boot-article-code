package ru.auchan.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import ru.auchan.backend.dto.ArticleShort;
import ru.auchan.backend.dto.ArticleResponse;
import ru.auchan.backend.dto.PageableResponse;
import ru.auchan.backend.entity.Article;
import ru.auchan.backend.exception.ResourceAlreadyExistsException;
import ru.auchan.backend.exception.ResourceNotFoundException;
import ru.auchan.backend.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты для ArticleService")
class ArticleServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleService articleService;

    @Captor
    private ArgumentCaptor<Article> articleCaptor;

    private Article article;
    private ArticleShort articleRequest;

    @BeforeEach
    void setUp() {
        article = Article.builder()
                .id(1L)
                .articleCode(1001L)
                .articleName("Молоко")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        articleRequest = ArticleShort.builder()
                .articleCode(1001L)
                .articleName("Молоко")
                .build();
    }

    @Nested
    @DisplayName("Тесты создания статьи")
    class CreateArticleTests {

        @Test
        @DisplayName("Успешное создание статьи")
        void createArticle_Success() {
            when(articleRepository.existsByArticleCode(1001L)).thenReturn(false);
            when(articleRepository.save(any(Article.class))).thenReturn(article);

            Article response = articleService.create(articleRequest);

            assertThat(response).isNotNull();
            assertThat(response.getArticleCode()).isEqualTo(1001L);
            assertThat(response.getArticleName()).isEqualTo("Молоко");

            verify(articleRepository, times(1)).existsByArticleCode(1001L);
            verify(articleRepository, times(1)).save(any(Article.class));

            verifyNoMoreInteractions(articleRepository);
        }

        @Test
        @DisplayName("Создание статьи с существующим кодом - ошибка")
        void createArticle_AlreadyExists_ThrowsException() {
            when(articleRepository.existsByArticleCode(1001L)).thenReturn(true);

            assertThatThrownBy(() -> articleService.create(articleRequest))
                    .isInstanceOf(ResourceAlreadyExistsException.class)
                    .hasMessageContaining("already exists");

            verify(articleRepository, never()).save(any(Article.class));
        }

        @Test
        @DisplayName("Создание статьи с захватом аргумента (используя ArgumentCaptor)")
        void createArticle_CapturesArticleArgument() {
            when(articleRepository.existsByArticleCode(1001L)).thenReturn(false);
            when(articleRepository.save(any(Article.class))).thenReturn(article);

            articleService.create(articleRequest);

            verify(articleRepository).save(articleCaptor.capture());
            Article capturedArticle = articleCaptor.getValue();

            assertThat(capturedArticle.getArticleCode()).isEqualTo(1001L);
            assertThat(capturedArticle.getArticleName()).isEqualTo("Молоко");
        }
    }

    @Nested
    @DisplayName("Тесты получения статьи")
    class GetArticleTests {

        @Test
        @DisplayName("Успешное получение статьи по коду")
        void getArticle_Success() {
            when(articleRepository.findByArticleCode(1001L)).thenReturn(Optional.of(article));

            Article response = articleService.findByCode(1001L);

            assertThat(response).isNotNull();
            assertThat(response.getArticleCode()).isEqualTo(1001L);
            assertThat(response.getArticleName()).isEqualTo("Молоко");

            verify(articleRepository, times(1)).findByArticleCode(1001L);
        }

        @Test
        @DisplayName("Получение несуществующей статьи - ошибка")
        void getArticle_NotFound_ThrowsException() {
            when(articleRepository.findByArticleCode(9999L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> articleService.findByCode(9999L))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessageContaining("not found");
        }

        @Test
        @DisplayName("Проверка, что метод findByArticleCode вызывается с правильным параметром")
        void getArticle_VerifiesCorrectParameter() {
            when(articleRepository.findByArticleCode(1001L)).thenReturn(Optional.of(article));

            articleService.findByCode(1001L);

            verify(articleRepository).findByArticleCode(eq(1001L));
        }
    }

    @Nested
    @DisplayName("Тесты обновления статьи")
    class UpdateArticleTests {

        @Test
        @DisplayName("Успешное обновление статьи")
        void updateArticle_Success() {
            ArticleShort updateRequest = ArticleShort.builder()
                    .articleCode(1001L)
                    .articleName("Молоко СТМ")
                    .build();

            when(articleRepository.findByArticleCode(1001L)).thenReturn(Optional.of(article));
            when(articleRepository.save(any(Article.class))).thenReturn(article);

            Article response = articleService.update(1001L, updateRequest);

            assertThat(response).isNotNull();
            assertThat(response.getArticleName()).isEqualTo("Молоко СТМ");

            verify(articleRepository).findByArticleCode(1001L);
            verify(articleRepository).save(any(Article.class));
        }

        @Test
        @DisplayName("Обновление несуществующей статьи - ошибка")
        void updateArticle_NotFound_ThrowsException() {
            when(articleRepository.findByArticleCode(9999L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> articleService.update(9999L, articleRequest))
                    .isInstanceOf(ResourceNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Тесты удаления статьи")
    class DeleteArticleTests {

        @Test
        @DisplayName("Успешное удаление статьи")
        void deleteArticle_Success() {
            when(articleRepository.deleteByArticleCode(1001L)).thenReturn(1L);

            long deleteCount = articleService.delete(1001L);
            assertThat(deleteCount).isEqualTo(1);

            verify(articleRepository, times(1)).deleteByArticleCode(1001L);
        }

        @Test
        @DisplayName("Удаление несуществующей статьи - ошибка")
        void deleteArticle_NotFound_ThrowsException() {
            assertThatThrownBy(() -> articleService.delete(9999L))
                    .isInstanceOf(ResourceNotFoundException.class);

            verify(articleRepository, times(1)).deleteByArticleCode(anyLong());
        }
    }
    @Test
    @DisplayName("получение всех статей с пагинацией - есть результаты")
    void findAll_ShouldReturnPageableResponse() {
        int page = 0;
        int size = 20;
        String sortBy = "id";
        String sortDirection = "ASC";
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy));
        Article article1 = Article.builder()
                .id(1L)
                .articleCode(1001L)
                .articleName("Молоко")
                .build();

        Article article2 = Article.builder()
                .id(2L)
                .articleCode(1002L)
                .articleName("Молоко СТМ")
                .build();

        List<Article> articles = List.of(article1, article2);
        Page<Article> articlePage = new PageImpl<>(articles, pageable, 2);

        when(articleRepository.findAll(pageable)).thenReturn(articlePage);

        PageableResponse<ArticleResponse> result = articleService.findAll(page, size, sortBy, sortDirection);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageNumber()).isEqualTo(0);
        assertThat(result.getPageSize()).isEqualTo(20);
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.isLast()).isTrue();

        assertThat(result.getContent().get(0).getArticleCode()).isEqualTo(1001L);
        assertThat(result.getContent().get(0).getArticleName()).isEqualTo("Молоко");
        assertThat(result.getContent().get(1).getArticleCode()).isEqualTo(1002L);
        assertThat(result.getContent().get(1).getArticleName()).isEqualTo("Молоко СТМ");
    }

    @Test
    @DisplayName("получение всех статей с пагинацией - нет результатов")
    void findAll_WhenNoArticles_ShouldReturnEmptyPageableResponse() {
        int page = 2;
        int size = 5;
        String sortBy = "articleName";
        String sortDirection = "DESC";

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
        Page<Article> emptyPage = new PageImpl<>(List.of(), pageable, 0);

        when(articleRepository.findAll(pageable)).thenReturn(emptyPage);

        PageableResponse<ArticleResponse> result = articleService.findAll(page, size, sortBy, sortDirection);

        assertThat(result.getContent()).isEmpty();
        assertThat(result.getPageNumber()).isEqualTo(2);
        assertThat(result.getPageSize()).isEqualTo(5);
        assertThat(result.getTotalElements()).isEqualTo(0);
        assertThat(result.getTotalPages()).isEqualTo(0);
        assertThat(result.isLast()).isTrue();
    }
}