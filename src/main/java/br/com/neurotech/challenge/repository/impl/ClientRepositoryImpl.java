package br.com.neurotech.challenge.repository.impl;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.repository.ClientRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

    private final Map<String, NeurotechClient> database = new HashMap<>();

    @Override
    public String save(NeurotechClient client) {
        String id = UUID.randomUUID().toString();
        database.put(id, client);
        return id;
    }

    @Override
    public NeurotechClient findById(String id) {
        return database.get(id);
    }

    @Override
    public Map<String, NeurotechClient> findAll() {
        return database;
    }
}
