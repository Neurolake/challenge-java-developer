package br.com.neurotech.challenge.service.impl;

import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.Repository.ClientRepository;
import br.com.neurotech.challenge.entity.NeurotechClient;
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
        return clientRepository.findById(id).orElse(null);
    }
}
