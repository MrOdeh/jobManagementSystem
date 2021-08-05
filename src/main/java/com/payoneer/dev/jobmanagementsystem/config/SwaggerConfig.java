package com.payoneer.dev.jobmanagementsystem.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Job Management System", version = "1.0",
        description = "Job Management Service was designed to handle custom requirements, The goal of this system is to handle the" +
                "execution of multiple types of Jobs.",
        termsOfService = "www.google.com", contact = @Contact(name = "Mahmoud Odeh", url = "/swagger-ui.html",
        email = "mahmoud.odehgs@gmail.com")))
public class SwaggerConfig {
}
