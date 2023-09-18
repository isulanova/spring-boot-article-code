package ru.auchan.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.auchan.plugin.metrics.annotation.EnableMetrics;
import ru.auchan.plugin.openapi.annotation.EnableOpenApi;

@EnableOpenApi
@EnableMetrics
@ConfigurationPropertiesScan
@EnableFeignClients
@SpringBootApplication
public class Application {

  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
