package ru.auchan.backend.config.metrics.config;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("app.monitoring")
public class MonitoringConfigProperties {

  @Value("${app.monitoring.enabled:false}")
  private boolean enabled;

  @Value("#{'${app.monitoring.indicators:cpu,ram,discovery}'.split(',')}")
  private List<String> indicators;

  @Value("${app.monitoring.normal.cpu.level.percent:75}")
  private BigDecimal normalCpuLevelPercent;

  @Value("${app.monitoring.fatal.cpu.level.percent:95}")
  private BigDecimal fatalCpuLevelPercent;

  @Value("${app.monitoring.normal.ram.level.percent:75}")
  private BigDecimal normalRamLevelPercent;

  @Value("${app.monitoring.fatal.ram.level.percent:95}")
  private BigDecimal fatalRamLevelPercent;
}
