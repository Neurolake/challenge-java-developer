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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.neurotech.challenge.DTO.ClientRequestDTO;
import br.com.neurotech.challenge.DTO.ClientResponseDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.exception.ClientNotFoundException;
import br.com.neurotech.challenge.mapper.ClientMapper;
import br.com.neurotech.challenge.service.ClientService;
import br.com.neurotech.challenge.service.CreditService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;
    private final CreditService creditService;

    public ClientController(ClientService clientService, CreditService creditService) {
        this.clientService = clientService;
        this.creditService = creditService;
    }

    @Operation(summary = "Cadastrar novo cliente", description = "Cria um cliente e retorna o Location com o ID")
    @PostMapping
    public ResponseEntity<Void> createClient(@RequestBody @Valid ClientRequestDTO clientRequestDTO) {
        NeurotechClient client = ClientMapper.toEntity(clientRequestDTO);
        String id = clientService.save(client);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Buscar cliente", description = "Busca o clinete por id")
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClient(@PathVariable String id) {
        NeurotechClient client = clientService.get(id);
        if (client == null) {
            throw new ClientNotFoundException(id);
        }
        ClientResponseDTO responseDTO = ClientMapper.toResponseDTO(client);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Verifca crédito", description = "Verifica credito pelo id do usuário")
    @GetMapping("{id}/credit")
    public ResponseEntity<Boolean> checkCredit(@PathVariable String id, @RequestParam VehicleModel vehicleModel) {
        NeurotechClient client = clientService.get(id);
        if (client == null) {
            throw new ClientNotFoundException(id);
        }
        boolean isEligible = creditService.checkCredit(id, vehicleModel);
        return ResponseEntity.ok(isEligible);
    }

    @Operation(summary = "Lista clientes", description = "Filtra os clientes especiais")
    @GetMapping("/special")
    public ResponseEntity<List<ClientResponseDTO>> listSpecialClients() {
        List<ClientResponseDTO> specialClients = clientService.findSpecialClients();
        return ResponseEntity.ok(specialClients);
    }
}
