package br.com.neurotech.challenge.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientResponseDTO {
    private String name;
    private Integer age;
    private Double income;

}
