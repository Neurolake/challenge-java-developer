package br.com.neurotech.challenge.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.DTO.ClientResponseDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;

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
	 * Lista os Clientes especiais
	 */
	List<ClientResponseDTO> findSpecialClients();

}
