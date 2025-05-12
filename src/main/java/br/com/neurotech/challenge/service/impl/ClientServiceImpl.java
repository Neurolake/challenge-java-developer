package br.com.neurotech.challenge.service.impl;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.repository.ClientRepository;
import br.com.neurotech.challenge.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public String save(NeurotechClient client) {
        return clientRepository.save(client);
    }

    @Override
    public NeurotechClient get(String id) {
        return clientRepository.findById(id);
    }
}
