package br.com.neurotech.challenge.service.impl;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.repository.ClientRepository;
import br.com.neurotech.challenge.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditServiceImpl implements CreditService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public boolean checkCredit(String clientId, VehicleModel model) {
        NeurotechClient client = clientRepository.findById(clientId);
        if (client == null) return false;

        switch (model) {
            case HATCH:
                return client.getIncome() >= 5000 && client.getIncome() <= 15000;
            case SUV:
                return client.getIncome() > 8000 && client.getAge() > 20;
            default:
                return false;
        }
    }
}
