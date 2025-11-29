package com.bd.gestion_bancaire.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientRegistrationRequest {

    @NotBlank @Size(max = 30)
    private String typeClient;
    @NotBlank @Size(max = 50)
    private String nom;
    @NotBlank @Size(max = 50)
    private String prenom;
    @NotBlank @Email @Size(max = 50)
    private String email;
    @NotBlank @Size(min = 6, max = 100)
    private String motDePasse;
    @NotBlank @Size(max = 20)
    private String telephone;
    @NotBlank @Size(max = 15)
    private String nationalite;
    @NotBlank @Size(max = 20)
    private String cinPassport;
    @NotBlank @Size(max = 20)
    private String raisonSociale;
    @NotBlank @Size(max = 30)
    private String adresse;
    private Long idAgence;
}
