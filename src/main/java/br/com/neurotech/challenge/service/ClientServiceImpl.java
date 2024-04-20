package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.exception.EntityNotFoundException;
import br.com.neurotech.challenge.exception.DuplicatedClientException;
import br.com.neurotech.challenge.exception.OperationUnsupportedException;
import br.com.neurotech.challenge.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.neurotech.challenge.constant.CreditConstants.JUROS_VARIAVEIS_MAX_INCOME;
import static br.com.neurotech.challenge.constant.CreditConstants.JUROS_VARIAVEIS_MIN_INCOME;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public String save(NeurotechClient client) {

        Optional<NeurotechClient> existingClient = clientRepository.findByName(client.getName());

        if (existingClient.isPresent()) {
            throw new DuplicatedClientException(
                    String.format("Client with name=%s already exists", client.getName())
            );
        }

        client.setCreditType(setCreditType(client.getAge(), client.getIncome()));
        NeurotechClient saved = clientRepository.save(client);

        return String.valueOf(saved.getId());
    }

    @Override
    public NeurotechClient get(String id) throws EntityNotFoundException {

        return clientRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Client not found for id=%s", id)));
    }

    @Override
    public List<NeurotechClient> findByAgeBetweenAndCreditType(Integer ageMin, Integer ageMax, CreditType creditType){
        return clientRepository.findByAgeBetweenAndCreditType(ageMin, ageMax, creditType);
    }

    private CreditType setCreditType(Integer age, Double income) {

        if (age > 65)
            return CreditType.CONSIGNADO;

        if (age >= 21 && income >= JUROS_VARIAVEIS_MIN_INCOME && income <= JUROS_VARIAVEIS_MAX_INCOME)
            return CreditType.JUROS_VARIAVEIS;

        if(age <=25){
            return CreditType.JUROS_FIXOS;
        }

        throw new OperationUnsupportedException("It is not possible to determine a credit type for this client");

    }
}
