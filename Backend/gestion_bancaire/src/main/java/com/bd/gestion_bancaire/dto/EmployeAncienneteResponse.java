package com.bd.gestion_bancaire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class EmployeAncienneteResponse {
    private String cin;
    private BigDecimal anciennete;
}
