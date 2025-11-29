package com.bd.gestion_bancaire.services;

import com.bd.gestion_bancaire.dto.OperationSimpleRequest;
import com.bd.gestion_bancaire.dto.TransactionResponse;
import com.bd.gestion_bancaire.dto.VirementRequest;
import com.bd.gestion_bancaire.helpers.OracleExceptionHelper;
import com.bd.gestion_bancaire.models.Transactions;
import com.bd.gestion_bancaire.repositories.CompteRepository;
import com.bd.gestion_bancaire.repositories.TransactionRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final CompteRepository compteRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(CompteRepository compteRepository, TransactionRepository transactionRepository) {
        this.compteRepository = compteRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionResponse> getTransactionsCompte(String ribCompte) {

        List<Transactions> transactions = transactionRepository.findAllByRib(ribCompte);

        return transactions.stream()
                .map(t -> mapToResponse(t, ribCompte))
                .collect(Collectors.toList());
    }

    private TransactionResponse mapToResponse(Transactions t, String monRib) {
        String direction = "CREDIT";

        if (t.getEmetteur().getRib().equals(monRib)) {
            direction = "DEBIT";

            if ("DEPOT".equalsIgnoreCase(t.getTypeT())) {
                direction = "CREDIT";
            }
        }

        return new TransactionResponse(
                t.getCodeT(),
                t.getTypeT(),
                t.getMontant(),
                t.getDateTransaction(),
                t.getEmetteur().getRib(),
                (t.getDestinataire() != null) ? t.getDestinataire().getRib() : null,
                direction
        );
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