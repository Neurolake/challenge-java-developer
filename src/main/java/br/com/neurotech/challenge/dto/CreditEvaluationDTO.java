package br.com.neurotech.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreditEvaluationDTO {
    private String cliente;
    private List<String> modalidadesAprovadas;
}
