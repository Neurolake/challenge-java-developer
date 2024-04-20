package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.exception.EntityNotFoundException;
import br.com.neurotech.challenge.exception.DuplicatedClientException;
import br.com.neurotech.challenge.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public String save(NeurotechClient client) {

        Optional<NeurotechClient> existingClient = clientRepository.findByName(client.getName());

        if (existingClient.isPresent()) {
            throw new DuplicatedClientException(
                    String.format("Client with name=%s already exists", client.getName())
            );
        }

        NeurotechClient saved = clientRepository.save(client);

        return String.valueOf(saved.getId());
    }

    @Override
    public NeurotechClient get(String id) throws EntityNotFoundException {

        return clientRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Client not found for id=%s", id)));
    }
}
