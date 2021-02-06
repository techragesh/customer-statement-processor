package com.rabo.customer.statement.processor.config;

import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger Configuration
 *
 * @author Ragesh Sharma
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Swagger bean configuration
     * @return Docket object
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.rabo.customer.statement.processor.controller"))
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo("Customer Statement Processor API",
                "Documentation for Customer Statement Processor",
                "1.0",
                "Terms of service for using customer statement processor app",
                new Contact("rageshsharma", "http://localhost:8090", "sharmaragi84@gmail.com"),
                "MIT Licence",
                "http://opensource.org/licences/MIT",
                new ArrayList<>()
             );
    }
}
