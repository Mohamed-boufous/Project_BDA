package com.bd.gestion_bancaire.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class VirementRequest {
    private Long ribEmetteur;
    private Long ribDestinataire;
    private BigDecimal montant;
}
