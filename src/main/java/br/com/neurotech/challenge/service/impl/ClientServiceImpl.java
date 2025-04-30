package br.com.neurotech.challenge.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.DTO.ClientResponseDTO;
import br.com.neurotech.challenge.Repository.ClientRepository;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.exception.ClientNotFoundException;
import br.com.neurotech.challenge.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public String save(NeurotechClient client) {
        return clientRepository.save(client);
    }

    @Override
    public NeurotechClient get(String id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    @Override
    public List<ClientResponseDTO> findSpecialClients() {
        return clientRepository.findAll().stream()
                .filter(client -> client.getAge() >= 23 && client.getAge() <= 25)
                .filter(client -> client.getIncome() >= 5000 && client.getIncome() <= 15000)
                .map(client -> new ClientResponseDTO(client.getName(), client.getAge(), client.getIncome()))
                .toList();
    }

}
