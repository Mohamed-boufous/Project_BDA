package com.bd.gestion_bancaire.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OperationSimpleRequest {
    private Long rib;
    private BigDecimal montant;
}
