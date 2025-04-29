package br.com.neurotech.challenge.Controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.neurotech.challenge.DTO.ClientRequestDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.mapper.ClientMapper;
import br.com.neurotech.challenge.service.ClientService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
        URI lication = URI.create("api/cleint" + id);
        return ResponseEntity.created(lication).build();
    }

}
