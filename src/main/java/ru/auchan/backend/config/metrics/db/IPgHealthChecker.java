package ru.auchan.backend.config.metrics.db;

import java.util.Optional;

public interface IPgHealthChecker {

    Optional<PgHealthChecker.DbStats> getHealth();
}
