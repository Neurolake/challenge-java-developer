package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.exception.ClientNotFoundException;
import br.com.neurotech.challenge.service.impl.CreditServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreditServiceImplTest {

    private ClientService clientService;
    private CreditServiceImpl creditService;

    @BeforeEach
    void setUp() {
        clientService = mock(ClientService.class);
        creditService = new CreditServiceImpl(clientService);
    }

    @Test
    void shouldReturnTrueForHatchWhenIncomeIsWithinRange() {
        String id = "hatch-ok";
        NeurotechClient client = createClient(30, 8000.0);
        when(clientService.get(id)).thenReturn(client);

        boolean eligible = creditService.checkCredit(id, VehicleModel.HATCH);
        assertTrue(eligible);
    }

    @Test
    void shouldReturnFalseForHatchWhenIncomeIsTooLow() {
        String id = "hatch-low";
        NeurotechClient client = createClient(30, 4000.0);
        when(clientService.get(id)).thenReturn(client);

        boolean eligible = creditService.checkCredit(id, VehicleModel.HATCH);
        assertFalse(eligible);
    }

    @Test
    void shouldReturnTrueForSuvWhenAgeAndIncomeAreValid() {
        String id = "suv-ok";
        NeurotechClient client = createClient(35, 9000.0);
        when(clientService.get(id)).thenReturn(client);

        boolean eligible = creditService.checkCredit(id, VehicleModel.SUV);
        assertTrue(eligible);
    }

    @Test
    void shouldReturnFalseForSuvWhenAgeIsTooLow() {
        String id = "suv-age-low";
        NeurotechClient client = createClient(19, 9000.0);
        when(clientService.get(id)).thenReturn(client);

        boolean eligible = creditService.checkCredit(id, VehicleModel.SUV);
        assertFalse(eligible);
    }

    @Test
    void shouldThrowExceptionWhenClientDoesNotExist() {
        String id = "missing";
        when(clientService.get(id)).thenReturn(null);

        assertThrows(ClientNotFoundException.class, () -> creditService.checkCredit(id, VehicleModel.HATCH));
    }

    private NeurotechClient createClient(int age, double income) {
        NeurotechClient client = new NeurotechClient();
        client.setAge(age);
        client.setIncome(income);
        return client;
    }
}
