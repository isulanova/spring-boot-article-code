package ru.auchan.backend.config.metrics.discovery;

import org.springframework.boot.actuate.health.Health;

/**
 * A health indicator interface specific to a DiscoveryClient implementation.
 *
 * @author Spencer Gibb
 */
public interface DiscoveryClientHealthIndicator {

  String getName();

  /**
   * Perform health check.
   *
   * @return An indication of health.
   */
  Health healthCheck();
}
