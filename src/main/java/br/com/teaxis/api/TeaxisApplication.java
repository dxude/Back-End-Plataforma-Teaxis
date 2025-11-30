package br.com.teaxis.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition; 
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info; 
import io.swagger.v3.oas.annotations.security.SecurityRequirement; 
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(title = "Teaxis API", version = "1", description = "API para conexão entre indivíduos neurodiversos e profissionais"),
    security = @SecurityRequirement(name = "bearerAuth") 
)

@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class TeaxisApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeaxisApplication.class, args);
	}

}