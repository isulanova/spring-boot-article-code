package ru.auchan.backend.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "spring.flyway.enabled", havingValue = "true")
@RequiredArgsConstructor
public class FlywayConfig {

  private static final String FLYWAY_SCHEMA = "spring.flyway.schemas";
  private static final String FLYWAY_DATASOURCE_URL = "spring.flyway.url";
  private static final String FLYWAY_DATASOURCE_USER = "spring.flyway.user";
  private static final String FLYWAY_DATASOURCE_PASSWORD = "spring.flyway.password";
  private static final String FLYWAY_MIGRATIONS_LOCATIONS = "spring.flyway.locations";
  private static final String FLYWAY_HISTORY_TABLE = "spring.flyway.table";
  private static final String FLYWAY_SCHEMA_PLACEHOLDER = "spring.flyway.placeholders.schema";

  @Bean("migration")
  public Flyway flyway(Environment env) {
    String schema = env.getRequiredProperty(FLYWAY_SCHEMA);
    return new Flyway(
        Flyway.configure()
            .baselineOnMigrate(true)
            .dataSource(
                env.getRequiredProperty(FLYWAY_DATASOURCE_URL),
                env.getRequiredProperty(FLYWAY_DATASOURCE_USER),
                env.getRequiredProperty(FLYWAY_DATASOURCE_PASSWORD))
            .schemas(schema)
            .connectRetries(3)
            .failOnMissingLocations(true)
            .encoding(StandardCharsets.UTF_8)
            .locations(resolveLocations(env.getRequiredProperty(FLYWAY_MIGRATIONS_LOCATIONS)))
            .table(env.getRequiredProperty(FLYWAY_HISTORY_TABLE))
            .outOfOrder(false)
            .placeholders(Map.of("schema", env.getRequiredProperty(FLYWAY_SCHEMA_PLACEHOLDER)))
            .defaultSchema(schema));
  }

  private String[] resolveLocations(String locationString) {
    return locationString.split(",");
  }
}
