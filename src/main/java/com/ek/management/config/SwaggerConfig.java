package com.ek.management.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi v1() {
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/v1/**")
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI(SpecVersion.V31)
                .info(this.getInfo());
    }

    private Info getInfo() {
        return new Info()
                .title("Management API")
                .contact(this.getContact())
                .version("1.0.0");
    }

    private Contact getContact() {
        return new Contact()
                .name("Pedro Emerick")
                .email("p.emerick@live.com");
    }
}
