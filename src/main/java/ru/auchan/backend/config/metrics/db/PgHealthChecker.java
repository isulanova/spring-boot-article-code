package ru.auchan.backend.config.metrics.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PgHealthChecker implements IPgHealthChecker {

  private final JdbcTemplate jdbcTemplate;

  public Optional<DbStats> getHealth() {
    return Optional.ofNullable(
        jdbcTemplate.queryForObject(getHealthCheckQuery(), new DbStatsRowMapper()));
  }

  private String getHealthCheckQuery() {
    return "SELECT current_database()                                   as dbname,\n"
        + "       pg_size_pretty(pg_database_size(current_database())) as dbSize,\n"
        + "       (SELECT count(*)\n"
        + "        FROM pg_stat_activity\n"
        + "        WHERE datname = current_database()\n"
        + "          and state = 'active')                             as connectionsActive";
  }

  static class DbStatsRowMapper implements RowMapper<DbStats> {
    @Override
    public DbStats mapRow(ResultSet rs, int rn) throws SQLException {
      DbStats stats = new DbStats();
      stats.setDbSize((rs.getString("dbSize")));
      stats.setDbName((rs.getString("dbname")));
      stats.setActiveConnections((rs.getInt("connectionsActive")));
      return stats;
    }
  }

  @Getter
  @Setter
  static class DbStats {
    private String dbName;
    private String dbSize;
    private int activeConnections;
  }
}
