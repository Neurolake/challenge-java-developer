package br.com.neurotech.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.neurotech.challenge.DTO.ClientResponseDTO;
import br.com.neurotech.challenge.Repository.ClientRepository;
import br.com.neurotech.challenge.entity.NeurotechClient;
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
                createClient("Maria", 30, 9000.0),
                createClient("Pedro", 24, 3000.0));

        when(clientRepository.findAll()).thenReturn(mockClients);

        List<ClientResponseDTO> result = clientServiceImpl.findSpecialClients();

        assertEquals(2, result.size());
        assertEquals("Laura", result.get(0).getFullName());
        assertEquals("André", result.get(1).getFullName());

    }

    private NeurotechClient createClient(String name, int age, double income) {
        NeurotechClient client = new NeurotechClient();
        client.setName(name);
        client.setAge(age);
        client.setIncome(income);
        return client;
    }

}
