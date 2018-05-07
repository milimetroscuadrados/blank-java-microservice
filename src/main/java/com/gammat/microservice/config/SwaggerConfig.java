package com.gammat.microservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by alejandro on 31/05/17.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket testApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("com.gammat.microservice.controller"))
                .paths(regex("/.*"))
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData(){
        ApiInfo apiInfo = new ApiInfo(
                "Microservice",
                "Microservice",
                "1.0",
                "Terms of Service",
                new Contact("Alejandro Sánchez",
                        "url",
                        "mail"),
                "Apache License version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0"
        );

        return apiInfo;
    }
}

