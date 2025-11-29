package com.bd.gestion_bancaire.controllers;

import com.bd.gestion_bancaire.dto.ErrorResponse;
import com.bd.gestion_bancaire.dto.OperationSimpleRequest;
import com.bd.gestion_bancaire.dto.VirementRequest;
import com.bd.gestion_bancaire.models.Client;
import com.bd.gestion_bancaire.repositories.ClientRepository;
import com.bd.gestion_bancaire.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final ClientRepository clientRepository;

    public TransactionController(TransactionService transactionService, ClientRepository clientRepository) {
        this.transactionService = transactionService;
        this.clientRepository = clientRepository;
    }

    @PreAuthorize("hasAuthority('ROLE_CLIENT')")
    @GetMapping("/mes-transactions")
    public ResponseEntity<?> getMesTransactions() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();

            Client client = clientRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Client introuvable"));

            if (client.getCompte() == null) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Vous n'avez pas encore de compte actif.", 400));
            }

            return ResponseEntity.ok(transactionService.getTransactionsCompte(client.getCompte().getRib()));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(), 400));
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_EMPLOYE', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    @GetMapping("/historique/{rib}")
    public ResponseEntity<?> getHistoriqueParRib(@PathVariable String rib) {
        try {
            return ResponseEntity.ok(transactionService.getTransactionsCompte(rib));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(e.getMessage(), 400)
            );
        }
    }

    @PostMapping("/depot")
    public ResponseEntity<?> depot(@RequestBody OperationSimpleRequest depot) {
        try {
            transactionService.faireDepot(depot);

            return ResponseEntity.ok(Map.of("message", "Dépôt effectué avec succès."));

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse(e.getMessage(), 400));
        }
    }

    @PostMapping("/retrait")
    public ResponseEntity<?> retrait(@RequestBody OperationSimpleRequest retrait) {
        try {
            transactionService.faireRetrait(retrait);

            return ResponseEntity.ok(Map.of("message", "Retrait effectué avec succès."));

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse(e.getMessage(), 400));
        }
    }

    @PostMapping("/virement")
    public ResponseEntity<?> virement(@RequestBody VirementRequest virement) {
        try {
            transactionService.faireVirement(virement);

            return ResponseEntity.ok(Map.of("message", "Virement effectué avec succès."));

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse(e.getMessage(), 400));
        }
    }
}