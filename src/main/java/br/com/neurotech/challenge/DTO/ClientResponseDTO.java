package br.com.neurotech.challenge.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "DTO de resposta com os dados do cliente")
@Getter
@AllArgsConstructor
public class ClientResponseDTO {

    @Schema(description = "Nome completo do cliente", example = "João Silva")
    private String name;

    @Schema(description = "Idade do cliente", example = "42")
    private Integer age;

    @Schema(description = "Renda mensal do cliente", example = "8500.00")
    private Double income;

}
