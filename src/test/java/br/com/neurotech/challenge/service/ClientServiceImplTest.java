package br.com.neurotech.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.neurotech.challenge.DTO.ClientResponseDTO;
import br.com.neurotech.challenge.Repository.ClientRepository;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.exception.ClientNotFoundException;
import br.com.neurotech.challenge.service.impl.ClientServiceImpl;

public class ClientServiceImplTest {

    private ClientRepository clientRepository;
    private ClientServiceImpl clientServiceImpl;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        clientServiceImpl = new ClientServiceImpl(clientRepository);
    }

    @Test
    void shouldReturnOnlySpecialClients() {
        List<NeurotechClient> mockClients = List.of(
                createClient("Laura", 24, 8000.0),
                createClient("André", 25, 14999.99),
                createClient("Carlos", 22, 7000.0),
                createClient("Maria", 50, 9000.0),
                createClient("Pedro", 24, 4000.0),
                createClient("Bruno", 18, 9000.0),
                createClient("Rico", 24, 20000.0));

        when(clientRepository.findAll()).thenReturn(mockClients);

        List<ClientResponseDTO> result = clientServiceImpl.findSpecialClients();

        assertEquals(2, result.size());
        assertEquals("Laura", result.get(0).getName());
        assertEquals("André", result.get(1).getName());
    }

    @Test
    void shouldSaveClientSuccessfully() {
        NeurotechClient client = createClient("Laura", 24, 8000.0);
        when(clientRepository.save(client)).thenReturn("generated-id");

        String id = clientServiceImpl.save(client);

        assertEquals("generated-id", id);
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void shouldReturnClientWhenIdExists() {
        String id = "123";
        NeurotechClient client = createClient("Laura", 24, 8000.0);
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        NeurotechClient result = clientServiceImpl.get(id);

        assertNotNull(result);
        assertEquals("Laura", result.getName());
        assertEquals(24, result.getAge());
        assertEquals(8000.0, result.getIncome());
    }

    @Test
    void shouldThrowClientNotFoundExceptionWhenIdDoesNotExist() {
        String id = "not-found";
        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientServiceImpl.get(id));
    }

    private NeurotechClient createClient(String name, int age, double income) {
        NeurotechClient client = new NeurotechClient();
        client.setName(name);
        client.setAge(age);
        client.setIncome(income);
        return client;
    }

}
