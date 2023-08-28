package ru.auchan.backend.config.openapi;

import static io.swagger.v3.oas.models.PathItem.HttpMethod.DELETE;
import static io.swagger.v3.oas.models.PathItem.HttpMethod.GET;
import static io.swagger.v3.oas.models.PathItem.HttpMethod.PATCH;
import static io.swagger.v3.oas.models.PathItem.HttpMethod.POST;
import static io.swagger.v3.oas.models.PathItem.HttpMethod.PUT;
import static java.util.Objects.isNull;
import static ru.auchan.backend.config.openapi.DefinitionStatus.ACCEPTED_DEF;
import static ru.auchan.backend.config.openapi.DefinitionStatus.BAD_REQUEST_DEF;
import static ru.auchan.backend.config.openapi.DefinitionStatus.CREATED_DEF;
import static ru.auchan.backend.config.openapi.DefinitionStatus.INTERNAL_SERVER_ERROR_DEF;
import static ru.auchan.backend.config.openapi.DefinitionStatus.NOT_FOUND_DEF;
import static ru.auchan.backend.config.openapi.DefinitionStatus.NO_CONTENT_DEF;
import static ru.auchan.backend.config.openapi.DefinitionStatus.OK_DEF;
import static ru.auchan.backend.config.openapi.DefinitionStatus.UNAUTHORIZED_DEF;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import ru.auchan.backend.config.openapi.exception.OpenApiConfigException;
import ru.auchan.backend.config.openapi.responses.ApiError400;
import ru.auchan.backend.config.openapi.responses.ApiError401;
import ru.auchan.backend.config.openapi.responses.ApiError404;
import ru.auchan.backend.config.openapi.responses.ApiError409;
import ru.auchan.backend.config.openapi.responses.ApiError500;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

  private final OpenApiProperties openApiProperties;

  @Bean
  public OpenAPI apiDefinition() {

    return new OpenAPI()
        .info(getInfo())
        .components(new Components().schemas(generateApiErrorResponseSchemas()))
        .servers(List.of(resolveRuntimeServer()))
        .specVersion(SpecVersion.V31);
  }

  @Bean
  public OpenApiCustomiser sortSchemasByTitleAlphabetically() {
    return openApi -> {
      final var sortedSchemas =
          openApi.getComponents().getSchemas().entrySet().stream()
              .filter(o -> !isNull(o.getValue().getTitle()))
              .sorted(Comparator.comparing(o -> o.getValue().getTitle()))
              .collect(
                  Collectors.toMap(
                      Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

      openApi.getComponents().setSchemas(sortedSchemas);
    };
  }

  @Bean
  public OpenApiCustomiser updateDefinitionDescriptions() {
    try {
      return openApi ->
          openApi
              .getPaths()
              .values()
              .forEach(pathItem -> pathItem.readOperationsMap().forEach(this::updateResponse));
    } catch (Exception ex) {
      log.error("Error while update definition descriptions");
      throw new OpenApiConfigException(ex.getMessage());
    }
  }

  private void updateResponse(PathItem.HttpMethod method, Operation operation) {
    final var apiResponses = operation.getResponses();

    if (method.equals(GET)) {
      updateResponseDefinition(HttpStatus.OK, apiResponses, OK_DEF.getStatus());
    }

    if (method.equals(POST)) {
      if (isNull(apiResponses.get(String.valueOf(HttpStatus.OK.value())))) {
        updateResponseDefinition(HttpStatus.CREATED, apiResponses, CREATED_DEF.getStatus());
      } else {
        updateResponseDefinition(HttpStatus.OK, apiResponses, OK_DEF.getStatus());
      }
    }

    if (method.equals(PUT) || method.equals(PATCH)) {
      updateResponseDefinition(HttpStatus.ACCEPTED, apiResponses, ACCEPTED_DEF.getStatus());
    }

    if (method.equals(DELETE)) {
      updateResponseDefinition(HttpStatus.NO_CONTENT, apiResponses, NO_CONTENT_DEF.getStatus());
    }

    updateResponseDefinition(HttpStatus.BAD_REQUEST, apiResponses, BAD_REQUEST_DEF.getStatus());
    updateResponseDefinition(HttpStatus.UNAUTHORIZED, apiResponses, UNAUTHORIZED_DEF.getStatus());
    updateResponseDefinition(HttpStatus.NOT_FOUND, apiResponses, NOT_FOUND_DEF.getStatus());
    updateResponseDefinition(
        HttpStatus.INTERNAL_SERVER_ERROR, apiResponses, INTERNAL_SERVER_ERROR_DEF.getStatus());
  }

  private void updateResponseDefinition(
      final HttpStatus httpStatus, final ApiResponses apiResponses, final String description) {
    final var code = String.valueOf(httpStatus.value());
    final var apiResponse = updateResponseInfo(apiResponses, code, description);
    apiResponses.put(code, apiResponse);
  }

  private ApiResponse updateResponseInfo(
      final ApiResponses apiResponses, final String code, final String description) {
    final var apiResponse = apiResponses.get(code);
    if (isNull(apiResponse)) {
      return new ApiResponse().description(description);
    }
    apiResponse.setDescription(description);
    return apiResponse;
  }

  private Info getInfo() {
    return new Info()
        .version(openApiProperties.getProjectVersion())
        .title(openApiProperties.getProjectTitle())
        .description(
            """
                              <br>
                              <h2>%s</h2>
                              <h3>Service info:</h3>"
                              <ul>
                                  <li>Application code: %s</li>
                                  <li>Application group: %s</li>
                                  <li>Gitlab URL: <a href=%s>Source code</a></li>
                               </ul>
                              <h3>Instance info:</h3>
                              <ul>
                                  <li>Used profile: %s</li>
                                  <li>SSL enabled: %s</li>
                                  <li>Service instance host: %s</li>
                                  <li>Service instance port: %s</li>
                              </ul>
                              <br>
                              """
                .formatted(
                    openApiProperties.getProjectDescription(),
                    openApiProperties.getApplicationCode(),
                    openApiProperties.getApplicationGroup(),
                    openApiProperties.getGitlabUrl(),
                    openApiProperties.getApplicationActiveProfile().toUpperCase(),
                    openApiProperties.getApplicationUseSSL(),
                    openApiProperties.getApplicationHost(),
                    openApiProperties.getApplicationPort()));
  }

  private Server resolveRuntimeServer() {
    final var runtimeServer = new Server();
    final var urlPrefix =
        Boolean.TRUE.equals(openApiProperties.getApplicationUseSSL()) ? "https://" : "http://";
    final var serviceURL =
        openApiProperties.getApplicationHost() + ":" + openApiProperties.getApplicationPort();
    runtimeServer.setUrl(urlPrefix + serviceURL);
    runtimeServer.setDescription(
        "Server URL in: "
            + openApiProperties.getApplicationActiveProfile().toUpperCase()
            + " environment");
    return runtimeServer;
  }

  private Map<String, Schema> generateApiErrorResponseSchemas() {
    final Map<String, Schema> schemaMap = new HashMap<>();

    schemaMap.putAll(parseSchemaClass(ApiError400.class));
    schemaMap.putAll(parseSchemaClass(ApiError401.class));
    schemaMap.putAll(parseSchemaClass(ApiError404.class));
    schemaMap.putAll(parseSchemaClass(ApiError409.class));
    schemaMap.putAll(parseSchemaClass(ApiError500.class));

    return schemaMap;
  }

  private Map<String, Schema> parseSchemaClass(final Class clazz) {
    return ModelConverters.getInstance().read(clazz);
  }
}
