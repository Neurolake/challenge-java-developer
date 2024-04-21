package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.dto.CreditAvailabilityStatusDto;
import br.com.neurotech.challenge.dto.EligibleNeurotechClientDto;
import br.com.neurotech.challenge.dto.NeurotechClientRegisterDto;
import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.exception.DuplicatedClientException;
import br.com.neurotech.challenge.exception.EntityNotFoundException;
import br.com.neurotech.challenge.service.ClientService;
import br.com.neurotech.challenge.service.CreditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @MockBean
    private CreditService creditService;

    private String USER_ID = "00c6fe3a-7eb3-417b-8a8a-257356735098";


    @Test
    void when_RegisterValidNeurotechClient_ReturnCreatedHttpStatus() throws Exception {

        String expectedClientId = USER_ID;
        NeurotechClientRegisterDto validNeurotechClient =
                new NeurotechClientRegisterDto("Luke Skywalker", 30, 100000.0);

        when(clientService.save(any(NeurotechClient.class))).thenReturn(expectedClientId);

        mockMvc.perform(post("/api/client/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonObjectAsString(validNeurotechClient)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/client/" + USER_ID));
    }

    @Test
    void when_RegisterInvalidNeurotechClient_ReturnBadRequestHttpStatus() throws Exception {

        NeurotechClientRegisterDto invalidNeurotechClient =
                new NeurotechClientRegisterDto(null, 26, 1000.0);

        mockMvc.perform(post("/api/client/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonObjectAsString(invalidNeurotechClient)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void when_RegisterDuplicatedNeurotechClient_ReturnConflictHttpStatus() throws Exception {

        NeurotechClientRegisterDto incomingNeurotechClient =
                new NeurotechClientRegisterDto("Harry Potter", 50, 2500.0);

        when(clientService.save(any(NeurotechClient.class))).thenThrow(DuplicatedClientException.class);

        mockMvc.perform(post("/api/client/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonObjectAsString(incomingNeurotechClient)))
                .andExpect(status().isConflict());
    }

    @Test
    void when_GetNeurotechClientById_ReturnRequestedClientAndOkHttpStatus() throws Exception {

        NeurotechClient neurotechClient = new NeurotechClient();
        neurotechClient.setId(UUID.fromString(USER_ID));
        neurotechClient.setName("Sponge Bob");
        neurotechClient.setAge(18);
        neurotechClient.setIncome(2500.0);
        neurotechClient.setCreditType(CreditType.JUROS_FIXOS);

        when(clientService.get(USER_ID)).thenReturn(neurotechClient);

        mockMvc.perform(get("/api/client/" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(getJsonObjectAsString(neurotechClient)));
    }

    @Test
    void when_GetNeurotechClientById_ReturnNotFoundHttpStatus() throws Exception {

        when(clientService.get(USER_ID)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/api/client/" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void when_CheckCreditAvailabilityForSUV_ReturnFalseAndOkHttpStatus() throws Exception {

        NeurotechClient neurotechClient =
                stubNeurotechClient(USER_ID, "Alfred Smith", 25, CreditType.JUROS_VARIAVEIS, 4999.0);

        CreditAvailabilityStatusDto expectedCreditAvailabilityStatus =
                new CreditAvailabilityStatusDto(USER_ID, VehicleModel.SUV.name(), false);

        when(clientService.get(USER_ID)).thenReturn(neurotechClient);

        mockMvc.perform(get("/api/client/" + USER_ID + "/check-credit-availability/SUV")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(getJsonObjectAsString(expectedCreditAvailabilityStatus)));
    }

    @Test
    void when_CheckCreditAvailabilityForSUV_ReturnTrueAndOkHttpStatus() throws Exception {

        NeurotechClient neurotechClient =
                stubNeurotechClient(USER_ID, "Alfred Smith", 25, CreditType.JUROS_VARIAVEIS, 8000.10);

        CreditAvailabilityStatusDto expectedCreditAvailabilityStatus =
                new CreditAvailabilityStatusDto(USER_ID, VehicleModel.SUV.name(), true);

        when(creditService.checkCredit(USER_ID, VehicleModel.SUV)).thenReturn(true);

        mockMvc.perform(get("/api/client/" + USER_ID + "/check-credit-availability/SUV")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(getJsonObjectAsString(expectedCreditAvailabilityStatus)));
    }

    @Test
    void when_CheckCreditAvailability_ReturnNotFoundHttpStatus() throws Exception {

        mockMvc.perform(get("/api/client/" + USER_ID + "/check-credit-availability/SEDAN")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void when_GetEligibleNeurotechClients_ReturnRequestedClientsAndOkHttpStatus() throws Exception {


        String expectedName = "Sponge Bob";
        Integer ageMin = 23;
        Integer ageMax = 40;
        Double expectedIncome = 5000.0;
        VehicleModel vehicleModel = VehicleModel.HATCH;

        NeurotechClient neurotechClient =
                stubNeurotechClient(USER_ID, expectedName, 25, CreditType.JUROS_FIXOS, 5000.0);

        when(clientService.get(USER_ID)).thenReturn(neurotechClient);

        when(creditService.getEligibleNeurotechClients(ageMin, ageMax, vehicleModel, CreditType.JUROS_FIXOS)).thenReturn(Set.of(neurotechClient));

        mockMvc.perform(get("/api/client/eligible-clients/?ageMin=" + ageMin + "&ageMax=" + ageMax + "&vehicleModel=" + vehicleModel)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(getJsonObjectAsString(Set.of(new EligibleNeurotechClientDto(expectedName, expectedIncome)))));
    }

    @Test
    void when_GetEligibleNeurotechClients_ReturnNoClientAndNotFoundHttpStatus() throws Exception {

        Integer ageMin = 23;
        Integer ageMax = 40;
        VehicleModel vehicleModel = VehicleModel.HATCH;

        when(creditService.getEligibleNeurotechClients(ageMin, ageMax, vehicleModel, CreditType.JUROS_FIXOS)).thenReturn(Set.of());

        mockMvc.perform(get("/api/client/eligible-clients/?ageMin=" + ageMin + "&ageMax=" + ageMax + "&vehicleModel=" + vehicleModel)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    private String getJsonObjectAsString(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    private NeurotechClient stubNeurotechClient(String id, String name, Integer age, CreditType creditType, Double income) {
        NeurotechClient neurotechClient = new NeurotechClient();
        neurotechClient.setId(UUID.fromString(id));
        neurotechClient.setName(name);
        neurotechClient.setAge(age);
        neurotechClient.setCreditType(creditType);
        neurotechClient.setIncome(income);

        return neurotechClient;
    }
}
