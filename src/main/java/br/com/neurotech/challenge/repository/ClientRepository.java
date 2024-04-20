package br.com.neurotech.challenge.repository;

import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.entity.NeurotechClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<NeurotechClient, UUID> {

    Optional<NeurotechClient> findByName(String name);

    List<NeurotechClient> findByAgeBetweenAndCreditType(Integer ageMin, Integer ageMax, CreditType creditType);
}
