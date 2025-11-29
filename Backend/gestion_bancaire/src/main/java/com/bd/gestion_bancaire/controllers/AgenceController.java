package com.bd.gestion_bancaire.controllers;

import com.bd.gestion_bancaire.dto.ErrorResponse;
import com.bd.gestion_bancaire.dto.SalaireTotalResponse;
import com.bd.gestion_bancaire.services.AgenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/admin")
public class AgenceController {
    private final AgenceService agenceService;

    public AgenceController(AgenceService agenceService) {
        this.agenceService = agenceService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/agences")
    public ResponseEntity<?> getAgences(){
        try {
            return ResponseEntity.ok(agenceService.getAllAgences());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(e.getMessage(), 400));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/agences/{idAgence}")
    public ResponseEntity<?> getAgence(@PathVariable Long idAgence){
        try {
            return ResponseEntity.ok(agenceService.getAgence(idAgence));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(e.getMessage(), 400));
        }
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/salaire-total/{idAgence}")
    public ResponseEntity<?> getSalaireTotalAgence(@PathVariable Long idAgence){
        try {
            BigDecimal salTotal = agenceService.getSalaireTotalAgence(idAgence);
            return ResponseEntity.ok(new SalaireTotalResponse(idAgence, salTotal));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(e.getMessage(), 400));
        }
    }


}
