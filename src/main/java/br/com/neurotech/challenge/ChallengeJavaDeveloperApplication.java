package br.com.neurotech.challenge;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Neurotech client API", version = "1.0", description = "Client and credit availability information"))
public class ChallengeJavaDeveloperApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeJavaDeveloperApplication.class, args);
	}

}
