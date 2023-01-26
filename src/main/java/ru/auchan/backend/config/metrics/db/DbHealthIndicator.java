package ru.auchan.backend.config.metrics.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.auchan.backend.config.metrics.config.HealthStatus;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DbHealthIndicator extends AbstractHealthIndicator {

    private final IPgHealthChecker pgHealthChecker;

    private static final String DB_SIZE = "dbSize";

    private static final String DB_NAME = "dbName";

    private static final String DB_ACTIVE_CONNECTIONS = "activeConnections";

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        try {
            Optional<PgHealthChecker.DbStats> statsOptional
                    = pgHealthChecker.getHealth();
            if (statsOptional.isPresent()) {
                PgHealthChecker.DbStats stats = statsOptional.get();
                builder.status(HealthStatus.OK.name())
                        .withDetail(DB_ACTIVE_CONNECTIONS, stats.getActiveConnections())
                        .withDetail(DB_NAME, stats.getDbName())
                        .withDetail(DB_SIZE, stats.getDbSize())
                        .build();
            } else {
                builder.status(new Status(Status.UNKNOWN.getCode()));
            }
        } catch (Exception ex) {
            log.error("Error while collect application DB metrics." +
                    "Message: {}", ex.getMessage());
            builder.status(new Status(HealthStatus.FATAL.name())
            ).build();
        }
    }
}
