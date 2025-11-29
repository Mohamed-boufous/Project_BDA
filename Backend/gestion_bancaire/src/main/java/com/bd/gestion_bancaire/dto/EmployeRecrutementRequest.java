package com.bd.gestion_bancaire.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeRecrutementRequest {
    @NotBlank @Size(max = 20)
    private String cin;

    @Size(max = 20)
    private String managerCin;

    @NotNull(message = "L'ID de l'agence est obligatoire.")
    private Long idAgence;

    @NotBlank @Size(max = 50)
    private String nom;

    @NotBlank @Size(max = 50)
    private String prenom;

    @NotBlank @Size(max = 50)
    private String poste;

    @NotNull(message = "Le salaire est obligatoire.")
    private Double salaire;

    @NotBlank @Size(min = 6, max = 100)
    private String motDePasse;

    @NotBlank @Email @Size(max = 50)
    private String email;

    @NotBlank @Size(max = 20)
    private String telephone;

}
