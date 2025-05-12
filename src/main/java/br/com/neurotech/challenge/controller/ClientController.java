package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.service.ClientService;
import br.com.neurotech.challenge.service.CreditService;
import br.com.neurotech.challenge.entity.VehicleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CreditService creditService;

    @PostMapping
    public ResponseEntity<Void> createClient(@RequestBody NeurotechClient client) {
        String id = clientService.save(client);
        return ResponseEntity.created(java.net.URI.create("/api/client/" + id))
                .header("Location", "/api/client/" + id)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NeurotechClient> getClient(@PathVariable String id) {
        NeurotechClient client = clientService.get(id);
        if (client == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(client);
    }

    @GetMapping("/{id}/check-credit")
    public ResponseEntity<Boolean> checkCredit(
            @PathVariable String id,
            @RequestParam VehicleModel model
    ) {
        boolean result = creditService.checkCredit(id, model);
        return ResponseEntity.ok(result);
    }
}
