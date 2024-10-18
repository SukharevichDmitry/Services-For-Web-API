package org.example.config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("spring-public")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public Info apiInfo() {
        return new Info()
                .title("API Documentation")
                .description("Описание API вашего сервиса")
                .version("1.0.0");
    }
}
