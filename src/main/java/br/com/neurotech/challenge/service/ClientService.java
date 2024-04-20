package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.CreditType;
import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.entity.NeurotechClient;

import java.util.List;

@Service
public interface ClientService {
	
	/**
	 * Salva um novo cliente
	 * 
	 * @return ID do cliente recém-salvo
	 */
	String save(NeurotechClient client);
	
	/**
	 * Recupera um cliente baseado no seu ID
	 */
	NeurotechClient get(String id);

	/**
	 * Obtem lista de clientes com base nas informações de filtro informadas.
	 */
	List<NeurotechClient> findByAgeBetweenAndCreditType(Integer ageMin, Integer ageMax, CreditType creditType);

}
