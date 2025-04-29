package br.com.neurotech.challenge.Repository;

import java.util.List;
import java.util.Optional;

import br.com.neurotech.challenge.entity.NeurotechClient;

public interface ClientRepository {
    String save(NeurotechClient client);

    Optional<NeurotechClient> findById(String id);

    List<NeurotechClient> findAll();

}
