package com.bd.gestion_bancaire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SalaireTotalResponse {
    private Long idAgence;
    private BigDecimal salaireTotal;
}
