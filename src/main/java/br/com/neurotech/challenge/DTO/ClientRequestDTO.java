package br.com.neurotech.challenge.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientRequestDTO {

    @NotBlank(message = "O mome é obrigatório")
    private String name;

    @NotNull(message = "A idade é obrigatória")
    @Min(value = 18, message = "Idade mínima é 18 anos")
    private Integer age;

    @NotNull(message = "A renda é obrigatória")
    @Min(value = 0, message = "A renda deve ser positiva")
    private Double income;
}
