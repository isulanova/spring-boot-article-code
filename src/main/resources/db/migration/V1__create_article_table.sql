CREATE TABLE IF NOT EXISTS articles (
id BIGSERIAL PRIMARY KEY,
article_code BIGINT NOT NULL UNIQUE,
article_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX idx_article_code ON articles(article_code);
CREATE INDEX idx_article_name ON articles(article_name);
