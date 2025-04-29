package br.com.neurotech.challenge.mapper;

import br.com.neurotech.challenge.DTO.ClientRequestDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;

public class ClientMapper {

    private ClientMapper() {
    }

    public static NeurotechClient toEntity(ClientRequestDTO dto) {
        NeurotechClient client = new NeurotechClient();
        client.setName(dto.getName());
        client.setAge(dto.getAge());
        client.setIncome(dto.getIncome());
        return client;
    }

}
