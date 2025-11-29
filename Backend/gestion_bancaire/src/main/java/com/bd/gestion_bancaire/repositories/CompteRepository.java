package com.bd.gestion_bancaire.repositories;

import com.bd.gestion_bancaire.models.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {

    @Procedure(procedureName = "depot")
    void effectuerDepot(@Param("p_RIB") Long rib, @Param("p_montant") BigDecimal montant);

    @Procedure(procedureName = "retrait")
    void effectuerRetrait(@Param("p_RIB") Long rib, @Param("p_montant") BigDecimal montant);

    @Procedure(procedureName = "transfert")
    void effectuerVirement(
            @Param("p_RIB_emetteur") Long ribEmetteur,
            @Param("p_RIB_destinataire") Long ribDestinataire,
            @Param("p_montant") BigDecimal montant
    );
}