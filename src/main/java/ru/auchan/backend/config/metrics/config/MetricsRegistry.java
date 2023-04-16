package ru.auchan.backend.config.metrics.config;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.HealthContributorRegistry;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MetricsRegistry {

  private final MonitoringConfigProperties configProperties;

  private final HealthContributorRegistry registry;

  @PostConstruct
  private void initHealthCheck() {
    if (isDisabled()) {
      unregisterAllContributors();
    }

    applyIndicators();
  }

  private boolean isDisabled() {
    return !configProperties.isEnabled() || configProperties.getIndicators().isEmpty();
  }

  private void applyIndicators() {
    registry.forEach(
        contributor -> {
          if (!configProperties.getIndicators().contains(contributor.getName())) {
            unregisterContributor(contributor.getName());
          }
        });
  }

  private void unregisterAllContributors() {
    registry.forEach(
        contributor -> {
          unregisterContributor(contributor.getName());
        });
  }

  private void unregisterContributor(String name) {
    registry.unregisterContributor(name);
  }
}
