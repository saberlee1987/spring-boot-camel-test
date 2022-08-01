package com.saber.spring_boot_camel_test.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI openAPI(
            @Value(value = "${service.swagger.springboot.title}") String swaggerTitle,
            @Value(value = "${service.swagger.version}") String swaggerVersion,
            @Value(value = "${service.swagger.springboot.description}") String swaggerDescription
    ) {
        return new OpenAPI()
                .info(new Info().title(swaggerTitle).version(swaggerVersion).description(swaggerDescription));
    }

}
