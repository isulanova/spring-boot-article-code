package ru.auchan.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        Components components = new Components()
                .addSecuritySchemes("bearerAuth", securityScheme);

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("bearerAuth");

        return new OpenAPI()
                .info(new Info()
                        .title("API с JWT")
                        .version("1.0"))
                .components(components)
                .addSecurityItem(securityRequirement);
    }

    @Bean
    public OperationCustomizer customizeOperation() {
        return (operation, handlerMethod) -> {
            String path = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequestMapping.class)
                    .value()[0];

            if (path != null && path.startsWith("/auth")) {
                operation.setSecurity(null);
            }
            return operation;
        };
    }
}
