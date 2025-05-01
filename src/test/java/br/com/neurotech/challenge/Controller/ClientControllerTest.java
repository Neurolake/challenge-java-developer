package br.com.neurotech.challenge.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.neurotech.challenge.DTO.ClientRequestDTO;
import br.com.neurotech.challenge.DTO.ClientResponseDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import br.com.neurotech.challenge.service.ClientService;
import br.com.neurotech.challenge.service.CreditService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ClientService clientService;

        @MockBean
        private CreditService creditService;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        void mustRegisterCustomerAndReturnCreatedWithLocation() throws Exception {
                ClientRequestDTO dto = new ClientRequestDTO();
                dto.setName("Maria Silva");
                dto.setAge(30);
                dto.setIncome(8000.00);

                String fakeId = "abc-123";
                org.mockito.Mockito.when(clientService.save(org.mockito.ArgumentMatchers.any()))
                                .thenReturn(fakeId);

                mockMvc.perform(post("/api/client")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                                .andExpect(status().isCreated())
                                .andExpect(header().string("Location", "http://localhost/api/client/" + fakeId));

        }

        @Test
        void shouldReturnBadRequestWhenAgeIsLessThan18() throws Exception {
                ClientRequestDTO dto = new ClientRequestDTO();
                dto.setName("João Menor");
                dto.setAge(17);
                dto.setIncome(8000.0);

                mockMvc.perform(post("/api/client")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnBadRequestWhenFullNameIsBlank() throws Exception {
                ClientRequestDTO dto = new ClientRequestDTO();
                dto.setName("");
                dto.setAge(30);
                dto.setIncome(8000.0);

                mockMvc.perform(post("/api/client")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnBadRequestWhenIncomeIsNegative() throws Exception {
                ClientRequestDTO dto = new ClientRequestDTO();
                dto.setName("Carlos Renda Negativa");
                dto.setAge(30);
                dto.setIncome(-1000.0);

                mockMvc.perform(post("/api/client")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnClientDataWhenClientExists() throws Exception {
                String clientId = "1234";
                NeurotechClient client = new NeurotechClient();
                client.setName("Maria Silva");
                client.setAge(30);
                client.setIncome(8000.0);

                org.mockito.Mockito.when(clientService.get(clientId)).thenReturn(client);

                mockMvc.perform(get("/api/client/" + clientId))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("Maria Silva"))
                                .andExpect(jsonPath("$.age").value(30))
                                .andExpect(jsonPath("$.income").value(8000.0));
        }

        @Test
        void shouldReturnNotFoundWhenClientDoesNotExist() throws Exception {
                String clientId = "9999";

                Mockito.when(clientService.get(clientId)).thenReturn(null);

                mockMvc.perform(get("/api/client/" + clientId))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.error")
                                                .value("Cliente com ID " + clientId + " não encontrado."));
        }

        @Test
        void shouldReturnTrueWhenClientIsEligibleForHatchCredit() throws Exception {
                String clientId = "1234";
                NeurotechClient client = new NeurotechClient();
                client.setName("Maria Silva");
                client.setAge(30);
                client.setIncome(8000.0);
                Mockito.when(clientService.get(clientId)).thenReturn(client);

                Mockito.when(creditService.checkCredit(clientId, VehicleModel.HATCH)).thenReturn(true);

                mockMvc.perform(get("/api/client/" + clientId + "/credit")
                                .param("vehicleModel", "HATCH"))
                                .andExpect(status().isOk())
                                .andExpect(content().string("true"));
        }

        @Test
        void shouldReturnFalseWhenClientIsNotEligibleForHatchCredit() throws Exception {
                String clientId = "1234";
                NeurotechClient client = new NeurotechClient();
                client.setName("Maria Silva");
                client.setAge(30);
                client.setIncome(8000.0);
                Mockito.when(clientService.get(clientId)).thenReturn(client);

                Mockito.when(creditService.checkCredit(clientId, VehicleModel.HATCH)).thenReturn(false);

                mockMvc.perform(get("/api/client/" + clientId + "/credit")
                                .param("vehicleModel", "HATCH"))
                                .andExpect(status().isOk())
                                .andExpect(content().string("false"));
        }

        @Test
        void shouldReturnNotFoundWhenCreditClientDoesNotExist() throws Exception {
                String clientId = "9999";
                Mockito.when(clientService.get(clientId)).thenReturn(null);

                mockMvc.perform(get("/api/client/" + clientId + "/credit")
                                .param("vehicleModel", "SUV"))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.error")
                                                .value("Cliente com ID " + clientId + " não encontrado."));
        }

        @Test
        void shouldReturnBadRequestWhenVehicleModelIsMissing() throws Exception {
                String clientId = "1234";
                NeurotechClient client = new NeurotechClient();
                client.setName("Maria Silva");
                client.setAge(30);
                client.setIncome(8000.0);

                Mockito.when(clientService.get(clientId)).thenReturn(client);

                mockMvc.perform(get("/api/client/" + clientId + "/credit"))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnSpecialClientsList() throws Exception {
                List<ClientResponseDTO> specialClients = List.of(
                                new ClientResponseDTO("Laura", 24, 9000.0),
                                new ClientResponseDTO("André", 25, 14000.0));

                Mockito.when(clientService.findSpecialClients()).thenReturn(specialClients);

                mockMvc.perform(get("/api/client/special"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(2))
                                .andExpect(jsonPath("$[0].name").value("Laura"))
                                .andExpect(jsonPath("$[1].income").value(14000.0));
        }

        @Test
        void shouldReturnEmptyListWhenNoSpecialClientsFound() throws Exception {
                Mockito.when(clientService.findSpecialClients()).thenReturn(List.of());

                mockMvc.perform(get("/api/client/special"))
                                .andExpect(status().isOk())
                                .andExpect(content().string("[]"));
        }

}
