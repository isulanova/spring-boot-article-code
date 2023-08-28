package ru.auchan.backend.config;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RunAfterStartup {

  private final ApplicationContext context;

  @EventListener(ApplicationReadyEvent.class)
  public void runAfterStartup() {
    final Flyway flyway = context.getBean(Flyway.class);
    flyway.migrate();
  }
}
