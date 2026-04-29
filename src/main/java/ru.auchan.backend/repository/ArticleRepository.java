package ru.auchan.backend.repository;

import ru.auchan.backend.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findByArticleCode(Long articleCode);

    @Transactional
    long deleteByArticleCode(Long articleCode);

    boolean existsByArticleCode(Long articleCode);
}