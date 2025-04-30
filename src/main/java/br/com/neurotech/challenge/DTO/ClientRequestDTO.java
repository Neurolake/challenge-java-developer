package br.com.neurotech.challenge.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "DTO de entrada para cadastro de cliente")
@Data
public class ClientRequestDTO {

    @Schema(description = "Nome completo do cliente", example = "Maria Silva")
    @NotBlank(message = "O nome é obrigatório")
    private String fullName;

    @Schema(description = "Idade do cliente", example = "30", minimum = "18")
    @NotNull(message = "A idade é obrigatória")
    @Min(value = 18, message = "Idade mínima é 18 anos")
    private Integer age;

    @Schema(description = "Renda mensal", example = "10000.0", minimum = "0")
    @NotNull(message = "A renda é obrigatória")
    @Min(value = 0, message = "A renda deve ser positiva")
    private Double income;
}
