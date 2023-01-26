package ru.auchan.backend.config.metrics.ram;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.auchan.backend.config.metrics.config.HealthStatus;
import ru.auchan.backend.config.metrics.config.MonitoringConfigProperties;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Component
@RequiredArgsConstructor
public class RamHealthIndicator extends AbstractHealthIndicator {

    private static final long GB_DIVIDER = 1024L * 1024 * 1024;

    private final MonitoringConfigProperties monitoringConfigProperties;
    private static final String MEMORY_USED = "usedGb";
    private static final String MEMORY_MAX = "maxGb";
    private static final String MEMORY_FREE = "freeGb";
    private static final String MEMORY_PERCENT_USED = "percentUsed";

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        try {
            RamUsage ramUsage = retrieveRamUsage();
            builder
                    .status(resolveMemoryUsageStatus(ramUsage
                            .getUsedMemoryPercent()))
                    .withDetail(MEMORY_USED, ramUsage.getUsedMemoryGb())
                    .withDetail(MEMORY_MAX, ramUsage.getMaxMemoryGb())
                    .withDetail(MEMORY_FREE, ramUsage.getMaxMemoryGb()
                            .subtract(ramUsage.getUsedMemoryGb()))
                    .withDetail(MEMORY_PERCENT_USED, ramUsage.getUsedMemoryPercent())
                    .build();
        } catch (Exception ex) {
            log.error("Error while collect application RAM metrics." +
                    "Message: {}", ex.getMessage());
            builder.status(
                    new Status(
                            Status.UNKNOWN.getCode())
            ).build();
        }
    }

    private RamUsage retrieveRamUsage() {
        RamUsage usage = new RamUsage();
        usage.setMaxMemoryGb(transformToGb(Runtime.getRuntime().maxMemory()));
        usage.setUsedMemoryGb(transformToGb(Runtime.getRuntime().totalMemory())
                .subtract(transformToGb(Runtime.getRuntime().freeMemory())));
        usage.resolveMemoryPercentUsage();
        return usage;
    }

    private BigDecimal transformToGb(Long value) {
        return BigDecimal.valueOf(value)
                .divide(BigDecimal.valueOf(GB_DIVIDER))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private Status resolveMemoryUsageStatus(BigDecimal usedMemoryPercent) {
        if (isNormal(usedMemoryPercent)) {
            return new Status(HealthStatus.OK.name());
        }

        if (isWarn(usedMemoryPercent)) {
            return new Status(HealthStatus.WARN.name());
        }

        if (isFatal(usedMemoryPercent)) {
            return new Status(HealthStatus.FATAL.name());
        }
        return new Status(Status.UNKNOWN.getCode());
    }

    private boolean isNormal(BigDecimal usedMemoryPercent) {
        return usedMemoryPercent
                .compareTo(monitoringConfigProperties
                        .getNormalRamLevelPercent()) < 0;
    }

    private boolean isWarn(BigDecimal usedMemoryPercent) {
        return usedMemoryPercent.compareTo(monitoringConfigProperties
                .getNormalRamLevelPercent()) > 0
                && usedMemoryPercent
                .compareTo(monitoringConfigProperties
                        .getFatalRamLevelPercent()) < 0;
    }

    private boolean isFatal(BigDecimal usedMemoryPercent) {
        return usedMemoryPercent
                .compareTo(monitoringConfigProperties
                        .getFatalRamLevelPercent()) > 0;
    }

    @Setter
    @Getter
    static class RamUsage {
        BigDecimal maxMemoryGb;
        BigDecimal usedMemoryGb;
        BigDecimal usedMemoryPercent;

        protected void resolveMemoryPercentUsage() {
            this.usedMemoryPercent = this.usedMemoryGb.multiply(BigDecimal.valueOf(100))
                    .divide(this.maxMemoryGb, 2, RoundingMode.HALF_UP);
        }
    }

}
