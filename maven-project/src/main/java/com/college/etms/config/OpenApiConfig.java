package com.college.etms.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI employeeTaskManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Employee Task Management System API")
                        .description("REST APIs for managing faculty and departmental tasks")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Engineering Department")
                                .email("admin@college.edu")));
    }
}
