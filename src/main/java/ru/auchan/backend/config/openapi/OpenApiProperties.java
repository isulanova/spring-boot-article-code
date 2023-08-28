package ru.auchan.backend.config.openapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "openapi")
@AllArgsConstructor
@Getter
public class OpenApiProperties {

  private final String projectTitle;
  private final String projectDescription;
  private final String projectVersion;
  private final String applicationCode;
  private final String applicationGroup;
  private final String applicationActiveProfile;
  private final Boolean applicationUseSSL;
  private final Integer applicationPort;
  private final String applicationHost;
  private final String gitlabUrl;
}
