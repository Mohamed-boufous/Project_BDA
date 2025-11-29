package com.bd.gestion_bancaire.services;

import com.bd.gestion_bancaire.helpers.OracleExceptionHelper;
import com.bd.gestion_bancaire.repositories.CompteRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CompteService {
    private final CompteRepository compteRepository;

    public CompteService(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    public void validerCompte(Long idClient){
        try {
            compteRepository.validerCompteClient(idClient);
        } catch (DataAccessException dae){
            throw new RuntimeException(OracleExceptionHelper.extractCleanMessage(dae));
        }

    }
}
