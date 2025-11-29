package com.bd.gestion_bancaire.controllers;

import com.bd.gestion_bancaire.dto.ErrorResponse;
import com.bd.gestion_bancaire.dto.MessageResponse;
import com.bd.gestion_bancaire.services.CompteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employe")
public class EmployeController {

    private final CompteService compteService;

    public EmployeController(CompteService compteService) {
        this.compteService = compteService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_EMPLOYE', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    @PostMapping("/valider-compte/{idClient}")
    public ResponseEntity<?> validerCompte(@PathVariable Long idClient) {
        try {
            compteService.validerCompte(idClient);
            return ResponseEntity.ok(new MessageResponse("Compte validé et activé avec succès."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(e.getMessage(), 400));
        }
    }

}
