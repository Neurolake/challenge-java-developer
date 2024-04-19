package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.exception.ClientNotFoundException;
import br.com.neurotech.challenge.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public String save(NeurotechClient client) {

        NeurotechClient saved = clientRepository.save(client);

        return String.valueOf(saved.getId());
    }

    @Override
    public NeurotechClient get(String id) throws ClientNotFoundException {

        return clientRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ClientNotFoundException(String.format("Client not found for id=%s", id)));
    }
}
