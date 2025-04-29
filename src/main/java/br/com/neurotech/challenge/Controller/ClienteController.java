package br.com.neurotech.challenge.Controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.neurotech.challenge.DTO.ClientRequestDTO;
import br.com.neurotech.challenge.DTO.ClientResponseDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.mapper.ClientMapper;
import br.com.neurotech.challenge.service.ClientService;
import br.com.neurotech.challenge.service.CreditService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/client")
public class ClienteController {

    private final ClientService clientService;
    private final CreditService creditService;

    public ClienteController(ClientService clientService, CreditService creditService) {
        this.clientService = clientService;
        this.creditService = creditService;
    }

    @PostMapping
    public ResponseEntity<Void> createClient(@RequestBody @Valid ClientRequestDTO clientRequestDTO) {
        NeurotechClient client = ClientMapper.toEntity(clientRequestDTO);
        String id = clientService.save(client);
        URI lication = URI.create("api/client/" + id);
        return ResponseEntity.created(lication).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClient(@PathVariable String id) {
        NeurotechClient client = clientService.get(id);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        ClientResponseDTO responseDTO = new ClientResponseDTO(client.getName(), client.getAge(), client.getIncome());
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("{id}/credit")
    public ResponseEntity<Boolean> checkCredit(@PathVariable String id, @RequestParam VehicleModel vehicleModel) {
        NeurotechClient client = clientService.get(id);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        boolean isEligible = creditService.checkCredit(id, vehicleModel);
        return ResponseEntity.ok(isEligible);
    }

    @GetMapping("/special")
    public ResponseEntity<List<ClientResponseDTO>> listSpecialClients() {
        List<ClientResponseDTO> specialClients = clientService.findSpecialClients();
        return ResponseEntity.ok(specialClients);
    }

}
