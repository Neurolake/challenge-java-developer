package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static br.com.neurotech.challenge.constant.CreditConstants.JUROS_VARIAVEIS_MAX_INCOME;
import static br.com.neurotech.challenge.constant.CreditConstants.JUROS_VARIAVEIS_MIN_INCOME;
import static br.com.neurotech.challenge.constant.CreditConstants.SUV_AGE_OLDER;
import static br.com.neurotech.challenge.constant.CreditConstants.SUV_INCOME_GREATER;

@Service
public class CreditServiceImpl implements CreditService {

    private final ClientService clientService;

    public CreditServiceImpl(ClientService clientService) {

        this.clientService = clientService;
    }

    @Override
    public boolean checkCredit(String clientId, VehicleModel model) {

        NeurotechClient neurotechClient = clientService.get(clientId);

        if (model.equals(VehicleModel.HATCH))
            return checkHATCHCredit(neurotechClient);

        return checkSUVCredit(neurotechClient);
    }

    private boolean checkHATCHCredit(NeurotechClient neurotechClient) {

        double income = neurotechClient.getIncome();

        return income >= JUROS_VARIAVEIS_MIN_INCOME && income <= JUROS_VARIAVEIS_MAX_INCOME;
    }

    private boolean checkSUVCredit(NeurotechClient neurotechClient) {

        return neurotechClient.getIncome() > SUV_INCOME_GREATER
                && neurotechClient.getAge() > SUV_AGE_OLDER;

    }

    @Override
    public Set<NeurotechClient> getEligibleNeurotechClients(Integer ageMin, Integer ageMax, VehicleModel vehicleModel, CreditType creditType){

        // Para esse requisito, a partir da leitura do enunciado, acredito que o cliente deve possui crédito do tipo Variável, e não Fixos.

        return clientService.findByAgeBetweenAndCreditType(
                        ageMin, ageMax, creditType)
                .stream()
                .filter(neurotechClient -> checkCredit(String.valueOf(neurotechClient.getId()), vehicleModel))
                .collect(Collectors.toSet());
    }
}
