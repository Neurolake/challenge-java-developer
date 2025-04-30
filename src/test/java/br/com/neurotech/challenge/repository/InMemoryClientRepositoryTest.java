package br.com.neurotech.challenge.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import br.com.neurotech.challenge.Repository.InMemoryClientRepository;
import br.com.neurotech.challenge.entity.NeurotechClient;

public class InMemoryClientRepositoryTest {
    private final InMemoryClientRepository repository = new InMemoryClientRepository();

    @Test
    void shouldSaveClientAndReturnId() {
        NeurotechClient client = createClient("Laura", 28, 7000.00);

        String id = repository.save(client);

        assertNotNull(id);
        Optional<NeurotechClient> saved = repository.findById(id);
        assertTrue(saved.isPresent());
        assertEquals("Laura", saved.get().getName());
    }

    @Test
    void shouldReturnEmptyWhenClientDoesNotExist() {
        Optional<NeurotechClient> result = repository.findById("noneexistent-id");
        assertTrue(result.isEmpty());
    }

    private NeurotechClient createClient(String name, int age, double income) {
        NeurotechClient client = new NeurotechClient();
        client.setName(name);
        client.setAge(age);
        client.setIncome(income);
        return client;
    }
}
