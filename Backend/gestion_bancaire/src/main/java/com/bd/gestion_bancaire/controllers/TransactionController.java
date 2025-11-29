package com.bd.gestion_bancaire.controllers;

import com.bd.gestion_bancaire.dto.ErrorResponse;
import com.bd.gestion_bancaire.dto.OperationSimpleRequest;
import com.bd.gestion_bancaire.dto.VirementRequest;
import com.bd.gestion_bancaire.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
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