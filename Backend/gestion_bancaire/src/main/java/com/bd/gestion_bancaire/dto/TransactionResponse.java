package com.bd.gestion_bancaire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private Long codeT;
    private String type;
    private BigDecimal montant;
    private Timestamp date;
    private String ribEmetteur;
    private String ribDestinataire;
    private String direction;
}
