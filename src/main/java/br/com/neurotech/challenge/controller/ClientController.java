package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.dto.CreditAvailabilityStatusDto;
import br.com.neurotech.challenge.dto.EligibleNeurotechClientDto;
import br.com.neurotech.challenge.dto.NeurotechClientRegisterDto;
import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.exception.EntityNotFoundException;
import br.com.neurotech.challenge.service.ClientService;
import br.com.neurotech.challenge.service.CreditService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

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

    @GetMapping("/eligible-clients/")
    public ResponseEntity<Set<EligibleNeurotechClientDto>> getEligibleNeurotechClients(
            @RequestParam(required = false) Integer ageMin,
            @RequestParam(required = false) Integer ageMax,
            @RequestParam(required = false) String vehicleModel) {

        try {
            VehicleModel vModel = VehicleModel.valueOf(vehicleModel.toUpperCase());

            Set<EligibleNeurotechClientDto> eligibleNeurotechClients =
                    creditService.getEligibleNeurotechClients(ageMin, ageMax, vModel, CreditType.JUROS_FIXOS)
                            .stream()
                            .map(neurotechClient -> new EligibleNeurotechClientDto(
                                    neurotechClient.getName(), neurotechClient.getIncome()))
                            .collect(Collectors.toSet());

            if(eligibleNeurotechClients.isEmpty())
                return ResponseEntity.notFound().build();

            return ResponseEntity.ok(eligibleNeurotechClients);

        } catch (IllegalArgumentException illegalArgumentException) {
            throw new EntityNotFoundException(String.format("Vehicle model '%s' not found", vehicleModel));
        }
    }

}
