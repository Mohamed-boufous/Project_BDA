package com.bd.gestion_bancaire.services;

import com.bd.gestion_bancaire.dto.OperationSimpleRequest;
import com.bd.gestion_bancaire.dto.VirementRequest;
import com.bd.gestion_bancaire.helpers.OracleExceptionHelper;
import com.bd.gestion_bancaire.repositories.CompteRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    private final CompteRepository compteRepository;

    public TransactionService(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    @Transactional
    public void faireDepot(OperationSimpleRequest request) {
        try {
            compteRepository.effectuerDepot(
                    request.getRib(),
                    request.getMontant()
            );
        } catch (DataAccessException dae) {
            throw new RuntimeException(OracleExceptionHelper.extractCleanMessage(dae));
        }
    }

    @Transactional
    public void faireRetrait(OperationSimpleRequest request) {
        try {
            compteRepository.effectuerRetrait(
                    request.getRib(),
                    request.getMontant()
            );
        } catch (DataAccessException dae) {
            throw new RuntimeException(OracleExceptionHelper.extractCleanMessage(dae));
        }
    }

    @Transactional
    public void faireVirement(VirementRequest request) {
        try {
            compteRepository.effectuerVirement(
                    request.getRibEmetteur(),
                    request.getRibDestinataire(),
                    request.getMontant()
            );
        } catch (DataAccessException dae) {
            throw new RuntimeException(OracleExceptionHelper.extractCleanMessage(dae));
        }
    }
}