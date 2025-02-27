package com.bytes.loans;

import com.bytes.loans.dto.LoansContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {LoansContactInfoDto.class})
@OpenAPIDefinition(
        info = @Info(
                title = "Accounts microservice REST API Documentation",
                description = "Bytes Accounts microsservice REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Gustavo",
                        email = "gustavo.oliveiradossantos@gmail.com",
                        url = "https://www.linkedin.com/in/gustavo-oliveira-santos/")
        ),
        externalDocs = @ExternalDocumentation(
                description = "Bytes Accounts microsservice REST API Documentation",
                url = "https://github.com/Guutooh/Bank-Byte-Microservices"
        )
)
public class LoansApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoansApplication.class, args);
    }
}