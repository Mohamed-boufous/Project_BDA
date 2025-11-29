package com.bd.gestion_bancaire.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AffectationRequest {

    @NotBlank
    private String cin;
    private String managerId;
    @NotNull
    private Long idAgence;
}
