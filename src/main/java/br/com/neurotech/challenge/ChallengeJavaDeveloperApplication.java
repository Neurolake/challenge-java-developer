package br.com.neurotech.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "Neurotech Credit Evaluation API", version = "1.0", description = "API para cadastro e avaliação de crédito de clientes PF"))
@SpringBootApplication
public class ChallengeJavaDeveloperApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeJavaDeveloperApplication.class, args);
	}
}
