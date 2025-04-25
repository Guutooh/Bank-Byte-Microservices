package com.bytes.cards;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(info = @Info(
		title = "Cards microservice REST API Documentation",
		description = "Bytes Accounts microsservice REST API Documentation",
		version = "v1",
		contact = @Contact(
				name = "Gustavo",
				email = "gustavo.oliveiradossantos@gmail.com",
				url = "https://www.linkedin.com/in/gustavo-oliveira-santos/")
),
		externalDocs = @ExternalDocumentation(
				description = "Bytes Cards microsservice REST API Documentation",
				url = "https://github.com/Guutooh/Bank-Byte-Microservices"

		)

)
@SpringBootApplication
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
