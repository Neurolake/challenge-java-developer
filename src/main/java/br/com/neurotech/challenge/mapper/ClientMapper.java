package br.com.neurotech.challenge.mapper;

import br.com.neurotech.challenge.DTO.ClientRequestDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;

public class ClientMapper {

    private ClientMapper() {
    }

    public static NeurotechClient toEntity(ClientRequestDTO dto) {
        return new NeurotechClient(dto.getName(), dto.getAge(), dto.getIncome());
    }

}
