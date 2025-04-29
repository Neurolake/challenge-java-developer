package br.com.neurotech.challenge.Controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.neurotech.challenge.DTO.ClientRequestDTO;
import br.com.neurotech.challenge.DTO.ClientResponseDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.mapper.ClientMapper;
import br.com.neurotech.challenge.service.ClientService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/client")
public class ClienteController {

    private final ClientService clientService;

    public ClienteController(ClientService clientService) {
        this.clientService = clientService;
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

}
