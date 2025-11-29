package com.bd.gestion_bancaire.controllers;

import com.bd.gestion_bancaire.dto.*;
import com.bd.gestion_bancaire.services.EmployeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/admin")
public class EmployeController {

    private final EmployeService employeService;

    public EmployeController(EmployeService employeService) {
        this.employeService = employeService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/employes")
    public ResponseEntity<?> getEmployes(){
        try {
            return ResponseEntity.ok(employeService.getAllEmployes());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(e.getMessage(), 400));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/employes/{cin}")
    public ResponseEntity<?> getEmploye(@PathVariable String cin){
        try {
            return ResponseEntity.ok(employeService.getEmploye(cin));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(e.getMessage(), 400));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/anciennete/{cin}")
    public ResponseEntity<?> calculerAnciennete(@PathVariable String cin){
        try {
            BigDecimal anciennete = employeService.calculerEmployeAnciennete(cin);
            return ResponseEntity.ok(new EmployeAncienneteResponse(cin, anciennete));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(e.getMessage(), 400));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping("/addEmploye")
    public ResponseEntity<?> recruterEmploye(@RequestBody EmployeRecrutementRequest request){
        try {
            employeService.recruterEmploye(request);
            return ResponseEntity.ok(new MessageResponse("Employe Recrute avec Succes ."));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new ErrorResponse("Erreur de recrutement : "+e.getMessage(), 400));
        }
    }

    @DeleteMapping("/licencierEmploye/{cin}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> licencierEmploye(@PathVariable String cin){
        try {
            employeService.licencierEmploye(cin);

            return ResponseEntity.ok(
                    new MessageResponse("Employe avec le CIN " + cin + " a ete licencie avec succes.")
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(e.getMessage(), 400)
            );
        }
    }

    @PutMapping("/affectation")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> changerAffectation(@RequestBody AffectationRequest affectRequest){
        try{
            employeService.changerAffectation(affectRequest);
            return ResponseEntity.ok(
                    new MessageResponse("Affectation d'Employe avec CIN "
                            +affectRequest.getCin()+
                            "a ete change avec succes")
            );
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(e.getMessage(), 400)
            );
        }
    }

    @PatchMapping("/augmenter-salaire/{cin}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> augmenterSalaire(@PathVariable String cin, @RequestBody BigDecimal taux_augmentation){
        try{
            employeService.augmenterSalaire(cin, taux_augmentation);
            return ResponseEntity.ok(
                    new MessageResponse("Augmentation de salaire d'Employe avec CIN "
                            +cin+ " de "+taux_augmentation+" a ete effectue avec succes")
            );
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(e.getMessage(), 400)
            );
        }
    }
}
