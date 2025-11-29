package com.bd.gestion_bancaire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class AgenceResponse {

        private Long idAgence;

        private String nomAgence;

        private String numTelephone;

        private String ville;

        private String codePostal;

}
