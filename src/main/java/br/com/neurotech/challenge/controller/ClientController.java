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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Neurotech Client", description = "Neurotech Client management APIs")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CreditService creditService;


    @PostMapping("/")
    @Operation(
            summary = "Creates a new NeurotechClient.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "409", content = { @Content(schema = @Schema()) }) })
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
    @Operation(
            summary = "Retrieves a NeurotechClient by id is exists.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = NeurotechClientRegisterDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<NeurotechClient> getNeurotechClientById(@PathVariable String id) {

        return ResponseEntity.ok(clientService.get(id));
    }

    @GetMapping("/{id}/check-credit-availability/{vehicleModel}")
    @Operation(
            summary = "Checks if a given NeurotechClient has credit availability for a vehicle model.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CreditAvailabilityStatusDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
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
    @Operation(
            summary = "List all the eligible NeurotechClient that has credit availability based on the query filters provided.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = EligibleNeurotechClientDto.class)), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
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
