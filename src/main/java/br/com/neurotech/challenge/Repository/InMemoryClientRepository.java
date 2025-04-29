package br.com.neurotech.challenge.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import br.com.neurotech.challenge.entity.NeurotechClient;

@Repository
public class InMemoryClientRepository implements ClientRepository {

    private final Map<String, NeurotechClient> database = new HashMap<>();

    @Override
    public String save(NeurotechClient client) {
        String id = UUID.randomUUID().toString();
        database.put(id, client);
        return id;
    }

    @Override
    public Optional<NeurotechClient> findById(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<NeurotechClient> findAll() {
        return new ArrayList<>(database.values());
    }

}
