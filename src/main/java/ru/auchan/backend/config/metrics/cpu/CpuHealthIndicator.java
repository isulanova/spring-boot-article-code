package ru.auchan.backend.config.metrics.cpu;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.auchan.backend.config.metrics.config.HealthStatus;
import ru.auchan.backend.config.metrics.config.MonitoringConfigProperties;

@Slf4j
@Component
@RequiredArgsConstructor
public class CpuHealthIndicator extends AbstractHealthIndicator {

  private static final String CPU_LOAD = "cpuLoad";
  private final MonitoringConfigProperties monitoringConfigProperties;

  @Override
  protected void doHealthCheck(final Health.Builder builder) {
    try {
      final BigDecimal cpuLoad = getProcessCpuLoad();
      builder.status(resolveCpuUsageStatus(cpuLoad)).withDetail(CPU_LOAD, cpuLoad).build();
    } catch (final Exception ex) {
      log.error("Error while collect application CPU metrics." + "Message: {}", ex.getMessage());
      builder.status(new Status(Status.UNKNOWN.getCode())).build();
    }
  }

  private Status resolveCpuUsageStatus(final BigDecimal usedCpuPercent) {
    if (isNormal(usedCpuPercent)) {
      return new Status(HealthStatus.OK.name());
    }

    if (isWarn(usedCpuPercent)) {
      return new Status(HealthStatus.WARN.name());
    }

    if (isFatal(usedCpuPercent)) {
      return new Status(HealthStatus.FATAL.name());
    }
    return new Status(Status.UNKNOWN.getCode());
  }

  private boolean isNormal(final BigDecimal usedCpuPercent) {
    return usedCpuPercent.compareTo(monitoringConfigProperties.getNormalCpuLevelPercent()) < 0;
  }

  private boolean isWarn(final BigDecimal usedCpuPercent) {
    return usedCpuPercent.compareTo(monitoringConfigProperties.getNormalCpuLevelPercent()) > 0
        && usedCpuPercent.compareTo(monitoringConfigProperties.getFatalCpuLevelPercent()) < 0;
  }

  private boolean isFatal(final BigDecimal usedCpuPercent) {
    return usedCpuPercent.compareTo(monitoringConfigProperties.getFatalCpuLevelPercent()) > 0;
  }

  private BigDecimal getProcessCpuLoad() {
    final OperatingSystemMXBean osBean =
        ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
    return transformToPercentUsage(osBean);
  }

  private BigDecimal transformToPercentUsage(final OperatingSystemMXBean osBean) {
    return BigDecimal.valueOf(osBean.getProcessCpuLoad() * 100).setScale(2, RoundingMode.HALF_UP);
  }
}
