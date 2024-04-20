package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.dto.CreditAvailabilityStatusDto;
import br.com.neurotech.challenge.dto.NeurotechClientRegisterDto;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.exception.EntityNotFoundException;
import br.com.neurotech.challenge.service.ClientService;
import br.com.neurotech.challenge.service.CreditService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CreditService creditService;


    @PostMapping("/")
    public ResponseEntity<Object> registerNeurotechClient(
            @Valid @RequestBody NeurotechClientRegisterDto neurotechClientRegisterDto) {

        NeurotechClient neurotechClient = new NeurotechClient();
        BeanUtils.copyProperties(neurotechClientRegisterDto, neurotechClient);

        String neurotechClientId = clientService.save(neurotechClient);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(neurotechClientId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NeurotechClient> getNeurotechClientById(@PathVariable String id) {

        return ResponseEntity.ok(clientService.get(id));
    }

    @GetMapping("/{id}/check-credit-availability/{vehicleModel}")
    public ResponseEntity<CreditAvailabilityStatusDto> checkCreditAvailability(
            @PathVariable String id, @PathVariable String vehicleModel) {

        try {
            VehicleModel vModel = VehicleModel.valueOf(vehicleModel.toUpperCase());

            return ResponseEntity.ok(
                    new CreditAvailabilityStatusDto(
                            id, vModel.name(), creditService.checkCredit(id, vModel)));

        } catch (IllegalArgumentException illegalArgumentException) {
            throw new EntityNotFoundException(String.format("Vehicle model '%s' not found", vehicleModel));
        }
    }

}
