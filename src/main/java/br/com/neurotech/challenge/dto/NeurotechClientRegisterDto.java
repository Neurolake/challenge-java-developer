package br.com.neurotech.challenge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NeurotechClientRegisterDto(

        @NotBlank(message = "The field name cannot be blank")
        String name,

        @Positive(message = "The age should be a positive number")
        @NotNull(message = "The field age cannot be null")
        Integer age,

        @Positive(message = "The age should be a positive number")
        @NotNull(message = "The field age cannot be null")
        Double income) {
}
