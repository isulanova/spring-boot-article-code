package ru.auchan.backend.config.metrics.discovery;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;

/**
 * @author Spencer Gibb
 */
@ConfigurationProperties("spring.cloud.discovery.client.health-indicator")
public class DiscoveryClientHealthIndicatorProperties {

  private boolean enabled = true;

  private boolean includeDescription = false;

  /**
   * Whether or not the indicator should use {@link DiscoveryClient#getServices} to check its
   * health. When set to {@code false} the indicator instead uses the lighter {@link
   * DiscoveryClient#probe()}. This can be helpful in large deployments where the number of services
   * returned makes the operation unnecessarily heavy.
   */
  private boolean useServicesQuery = true;

  public boolean isEnabled() {
    return this.enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isIncludeDescription() {
    return this.includeDescription;
  }

  public void setIncludeDescription(boolean includeDescription) {
    this.includeDescription = includeDescription;
  }

  public boolean isUseServicesQuery() {
    return useServicesQuery;
  }

  public void setUseServicesQuery(boolean useServicesQuery) {
    this.useServicesQuery = useServicesQuery;
  }

  @Override
  public String toString() {
    return "{"
        + "enabled="
        + this.enabled
        + ", includeDescription="
        + this.includeDescription
        + ", useServicesQuery="
        + this.useServicesQuery
        + '}';
  }
}
