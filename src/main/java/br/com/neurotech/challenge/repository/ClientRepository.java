package br.com.neurotech.challenge.repository;

import br.com.neurotech.challenge.entity.NeurotechClient;
import java.util.Map;

public interface ClientRepository {
    String save(NeurotechClient client);
    NeurotechClient findById(String id);
    Map<String, NeurotechClient> findAll();
}
