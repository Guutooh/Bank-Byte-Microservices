package com.bytes.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import jakarta.persistence.Version;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(info = @Info(
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
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

}
