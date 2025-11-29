package com.bd.gestion_bancaire.repositories;

import com.bd.gestion_bancaire.models.Agence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AgenceRepository extends JpaRepository<Agence, Long> {
    Optional<Agence> findAgenceByIdAgence(Long agenceId);

    @Query(value = "SELECT F_SALAIRE_TOTAL_AGENCE(:p_id_agence) FROM DUAL", nativeQuery = true)
    BigDecimal calculerSalaireTotalAgence(@Param("p_id_agence") Long idAgence);

}
