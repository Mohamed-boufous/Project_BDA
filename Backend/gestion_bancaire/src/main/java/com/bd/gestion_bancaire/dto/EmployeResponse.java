package com.bd.gestion_bancaire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class EmployeResponse {
    private String cin;

    private String nom;

    private String prenom;

    private String poste;

    private BigDecimal salaire;

    private Timestamp dateRecrutement;

    private String email;

    private String telephone;
}
