package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.entity.NeurotechClient;
import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.entity.VehicleModel;

import java.util.Set;

@Service
public interface CreditService {
	
	/**
	 * Efetua a checagem se o cliente está apto a receber crédito
	 * para um determinado modelo de veículo
	 */
	boolean checkCredit(String clientId, VehicleModel model);

	/**
	 * Recupera os clientes elegiveis para crédito com base nas informações de
	 * idade, tipo de veículo e modalidade de crédito.
	 */
	Set<NeurotechClient> getEligibleNeurotechClients(
			Integer ageMin, Integer ageMax,
			VehicleModel vehicleModel, CreditType creditType);
}
