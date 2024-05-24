package com.amcef.svancarek.testovaciezadanie.postservice.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("post-service-api")
                .packagesToScan("com.amcef.svancarek.testovaciezadanie.postservice.controller")
                .pathsToMatch("/api/**")
                .build();
    }
}