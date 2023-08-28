package ru.auchan.backend.config.metrics.discovery;

import java.util.concurrent.atomic.AtomicBoolean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import ru.auchan.backend.config.metrics.config.HealthStatus;

@Slf4j
@Component
@RequiredArgsConstructor
public class DiscoveryHealthIndicator extends AbstractHealthIndicator
    implements DiscoveryClientHealthIndicator,
        Ordered,
        ApplicationListener<InstanceRegisteredEvent<?>> {

  private final ObjectProvider<DiscoveryClient> discoveryClient;

  private final DiscoveryClientHealthIndicatorProperties properties;

  private final AtomicBoolean discoveryInitialized = new AtomicBoolean(false);

  private final int order = Ordered.HIGHEST_PRECEDENCE;

  @Override
  public void onApplicationEvent(final InstanceRegisteredEvent<?> event) {
    if (this.discoveryInitialized.compareAndSet(false, true)) {
      DiscoveryHealthIndicator.log.debug("Discovery Client has been initialized");
    }
  }

  @Override
  public Health healthCheck() {
    final Health.Builder builder = new Health.Builder();

    if (this.discoveryInitialized.get()) {
      try {
        final DiscoveryClient client = this.discoveryClient.getIfAvailable();

        if (properties.isUseServicesQuery()) {
          builder.status(new Status(HealthStatus.OK.name()));
        } else {
          client.probe();
          builder.status(new Status(HealthStatus.OK.name()));
        }
      } catch (final Exception e) {
        DiscoveryHealthIndicator.log.error("Error", e);
        builder.status(new Status(HealthStatus.FATAL.name()));
      }
    } else {
      builder.status(new Status(Status.UNKNOWN.getCode()));
    }
    return builder.build();
  }

  @Override
  public String getName() {
    return "discoveryClient";
  }

  @Override
  public int getOrder() {
    return this.order;
  }

  @Override
  protected void doHealthCheck(final Health.Builder builder) {
    builder.status(healthCheck().getStatus()).withDetails(healthCheck().getDetails()).build();
  }
}
