CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(25) NOT NULL UNIQUE,
    password VARCHAR(150) NOT NULL
    );

CREATE INDEX idx_login ON users(login);
